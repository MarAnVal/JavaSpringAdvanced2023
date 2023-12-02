package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.UserEditDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.AdminController;
import bg.softuni.aquagateclient.web.error.TopicNotFoundException;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Controller
public class AdminControllerImpl implements AdminController {
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public AdminControllerImpl(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public ModelAndView pending() {
        try {
            List<TopicView> allNotApprovedTopicsViews = topicService.getAllNotApprovedTopics();

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allNotApprovedTopicsViews);

            return model;

        } catch (TopicNotFoundException e) {
            //TODO ExceptionHandler
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView pendingDetails(Long id) {
        try {
            TopicDetailsView topicDetails = topicService.getTopicDetails(id);
            ModelAndView model = new ModelAndView("topic-detail" + id);
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
    public ModelAndView remove(Long id) {
        try {
            topicService.removeTopic(id);
            return new ModelAndView("redirect:/admin/pending");

        } catch (TopicNotFoundException e) {
            //TODO ExceptionHandler handling
            ModelAndView model = new  ModelAndView("error");
            model.addObject("statusCode", e.getStatusCode());
            model.addObject("message", e.getMessage());
            return model;
        }
    }

    @Override
    public ModelAndView approve(Long id) {
        try {
            topicService.approveTopic(id);

            return new ModelAndView("redirect:/admin/pending");
        } catch (TopicNotFoundException e) {
            //TODO ExceptionHandler
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView editUser() {
        return new ModelAndView("edit");
    }

    @Override
    public UserEditDTO initEditUserForm() {
        return new UserEditDTO();
    }

    @Override
    public ModelAndView doEditUser(UserEditDTO userEditDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO",
                            bindingResult);

            modelAndView.setViewName("redirect:admin/users-editUser");
        } else {
            //TODO ExceptionHandler
            try {
                this.userService.editUser(userEditDTO);
                modelAndView.setViewName("redirect:/");

            } catch (UserNotFoundException | RoleNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return modelAndView;
    }
}
