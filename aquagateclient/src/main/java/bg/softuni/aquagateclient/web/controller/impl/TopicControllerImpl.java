package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.TopicController;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class TopicControllerImpl implements TopicController {
    private final TopicService topicService;
    private final UserService userService;


    @Autowired
    public TopicControllerImpl(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public ModelAndView allApprovedTopics() throws BadRequestException {
            List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allApprovedTopics);
            return model;
    }

    @Override
    public TopicAddDTO initAddTopicForm() {
        return new TopicAddDTO();
    }

    @Override
    public ModelAndView addTopic() {
        return new ModelAndView("add-topic");
    }

    @Override
    public ModelAndView doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws IOException, BadRequestException, ObjectNotFoundException {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            modelAndView.setViewName("redirect:/topics/add");
        } else {

                UserEntity userByUsername = userService.findUserByUsername(principal.getName());
                topicAddDTO.setAuthor(userByUsername.getId());
                this.topicService.addTopic(topicAddDTO);

                modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @Override
    public ModelAndView topicDetails(Long id) throws BadRequestException, ObjectNotFoundException {

            TopicDetailsView topicDetails = topicService.getTopicDetails(id);

            ModelAndView model = new ModelAndView("topic-details");
            model.addObject("topicsDetailsView", topicDetails);

            return model;
    }

    @Override
    public ModelAndView latestTopicDetails() throws ObjectNotFoundException, BadRequestException {

            TopicDetailsView topicView = topicService.getLatestTopic();
            return new ModelAndView("redirect:/topics/details/" + topicView.getId());
    }

    @Override
    public ModelAndView myTopics(Principal principal) throws BadRequestException, ObjectNotFoundException {
            UserEntity userByUsername = userService.findUserByUsername(principal.getName());
            List<TopicView> allMyApprovedTopicViews = topicService.getAllTopicsByUserId(userByUsername.getId());

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allMyApprovedTopicViews);
            return model;
    }

    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @ExceptionHandler({IOException.class})
    public ModelAndView handleUserNotFound() {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "Picture failed to upload!");
        modelAndView.addObject("statusCode", 400);

        return modelAndView;
    }

}
