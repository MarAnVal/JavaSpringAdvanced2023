package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.data.entity.UserEntity;
import bg.softuni.aquagateclient.data.model.TopicAddDTO;
import bg.softuni.aquagateclient.data.view.TopicDetailsView;
import bg.softuni.aquagateclient.data.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.TopicController;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<TopicView> allApprovedTopicsViews = topicService.getAllApprovedTopics();

        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allApprovedTopicsViews);
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
    public String doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            return "redirect:topics/add";
        }
        Long id = null;
        try {
            UserEntity userByUsername = userService.findUserByUsername(principal.getName());
            id = userByUsername.getId();
        } catch (UserNotFoundException e) {
            //TODO ExceptionHandler
        }
        topicAddDTO.setUserId(id);

        this.topicService.addTopic(topicAddDTO);

        return "redirect:/";
    }

    @Override
    public ModelAndView topicDetails(Long id) {
        TopicDetailsView topicDetailsView = topicService.getTopicDetails(id);

        ModelAndView model = new ModelAndView("topic-details");
        model.addObject("topicsDetailsView", topicDetailsView);

        return model;
    }

    @Override
    public ModelAndView latestTopicDetails() {
        TopicView topicView = topicService.getLatestTopic();
        return new ModelAndView("redirect:/topics/details/" + topicView.getId());
    }

    @Override
    public ModelAndView mostCommentedTopicDetails() {
        TopicView topicView = topicService.getMostCommentedTopic();
        return new ModelAndView("redirect:/topics/details/" + topicView.getId());
    }

    @Override
    public ModelAndView myTopics(Principal principal) {
        Long id = null;
        try {
            UserEntity userByUsername = userService.findUserByUsername(principal.getName());
            id = userByUsername.getId();
        } catch (UserNotFoundException e) {
            //TODO ExceptionHandler
        }
        List<TopicView> allMyApprovedTopicViews = topicService.getAllTopicsByUserId(id);

        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allMyApprovedTopicViews);
        return model;
    }
}
