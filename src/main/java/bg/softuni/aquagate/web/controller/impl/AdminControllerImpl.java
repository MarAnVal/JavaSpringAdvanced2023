package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.service.UserService;
import bg.softuni.aquagate.web.controller.AdminController;
import bg.softuni.aquagate.web.error.CommentNotFoundException;
import bg.softuni.aquagate.web.error.PictureNotFoundException;
import bg.softuni.aquagate.web.error.TopicNotFoundException;
import bg.softuni.aquagate.web.error.UserNotFoundException;
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
        List<Topic> allNotApprovedTopics;
        try {
            allNotApprovedTopics = topicService.getAllNotApprovedTopics();
        } catch (TopicNotFoundException e) {
            ModelAndView model = new ModelAndView("/error");
            model.addObject("statusCode", e.getStatusCode());
            model.addObject("message", e.getMessage());
            return model;
        }
        List<TopicsView> allNotApprovedTopicsViews = topicService.mapTopicDetailsViewList(allNotApprovedTopics);

        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allNotApprovedTopicsViews);

        return model;
    }

    @Override
    public ModelAndView pendingDetails(Long id) {
        Topic byId;
        try {
            byId = topicService.findById(id);
        } catch (TopicNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }

        TopicsDetailsView topicsDetailsView = topicService.mapTopicsDetailsView(byId);

        ModelAndView model = new ModelAndView("topic-detail" + id);
        model.addObject("topicsDetailsView", topicsDetailsView);

        return model;
    }

    @Override
    public ModelAndView remove(Long id) {
        try {
            topicService.remove(id);
        } catch (CommentNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        } catch (TopicNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        } catch (PictureNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }

        return new ModelAndView("redirect:/admin/approve");
    }

    @Override
    public ModelAndView approve(Long id) {
        try {
            topicService.approve(id);
        } catch (TopicNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }
        return new ModelAndView("redirect:/admin/approve");
    }

    @Override
    public UserEditDTO initEditUserForm() {
        return new UserEditDTO();
    }

    @Override
    public ModelAndView editUser() {
        return new ModelAndView("redirect:/admin/edit");
    }

    @Override
    public String doEditUser(UserEditDTO userEditDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO",
                            bindingResult);

            return "redirect:admin/users-edit";
        }
        try {
            this.userService.edit(userEditDTO);
        } catch (RoleNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", 404);
            redirectAttributes.addFlashAttribute("message", "Role not found!");
            return "redirect:/error";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        }

        return "redirect:/";
    }
}
