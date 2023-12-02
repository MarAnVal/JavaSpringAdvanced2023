package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.TopicController;
import bg.softuni.aquagateclient.web.error.TopicNotFoundException;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public ModelAndView allApprovedTopics() {
        try {
            List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allApprovedTopics);
            return model;
        } catch (TopicNotFoundException e) {
            //TODO error Handler
            throw new RuntimeException(e);
        }
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
                             Principal principal) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            modelAndView.setViewName("redirect:/topics/add");
        } else {

            try {
                UserEntity userByUsername = userService.getUserByUsername(principal.getName());
                topicAddDTO.setAuthor(userByUsername.getId());
                this.topicService.addTopic(topicAddDTO);

                modelAndView.setViewName("redirect:/");

            } catch (UserNotFoundException e) {
                //TODO ExceptionHandler
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return modelAndView;
    }

    @Override
    public ModelAndView topicDetails(Long id) {
        try {
            TopicDetailsView topicDetails = topicService.getTopicDetails(id);

            ModelAndView model = new ModelAndView("topic-details");
            model.addObject("topicsDetailsView", topicDetails);

            return model;
        } catch (TopicNotFoundException e) {
            //TODO ExceptionHandler
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView latestTopicDetails() {
        try {
            TopicView topicView = topicService.getLatestTopic();
            return new ModelAndView("redirect:/topics/details/" + topicView.getId());
        } catch (TopicNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView myTopics(Principal principal) {
        try {
            UserEntity userByUsername = userService.getUserByUsername(principal.getName());
            List<TopicView> allMyApprovedTopicViews = topicService.getAllTopicsByUserId(userByUsername.getId());

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allMyApprovedTopicViews);
            return model;

        } catch (UserNotFoundException e) {
            //TODO ExceptionHandler
            throw new RuntimeException(e);
        } catch (TopicNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
