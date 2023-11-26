package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.data.model.UserEditDTO;
import bg.softuni.aquagateclient.data.view.TopicDetailsView;
import bg.softuni.aquagateclient.data.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.AdminController;
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
        List<TopicView> allNotApprovedTopicsViews = topicService.getAllPendingTopics();
        //TODO ExceptionHandler
        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allNotApprovedTopicsViews);

        return model;
    }

    @Override
    public ModelAndView pendingDetails(Long id) {
        TopicDetailsView topicDetailsView = topicService.getTopicDetails(id);
        //TODO ExceptionHandler
        ModelAndView model = new ModelAndView("topic-detail" + id);
        model.addObject("topicsDetailsView", topicDetailsView);

        return model;
    }

    @Override
    public ModelAndView remove(Long id) {
        topicService.remove(id);
        //TODO ExceptionHandler
        return new ModelAndView("redirect:/admin/approve");
    }

    @Override
    public ModelAndView approve(Long id) {
        topicService.approve(id);
        //TODO ExceptionHandler
        return new ModelAndView("redirect:/admin/approve");
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
    public String doEditUser(UserEditDTO userEditDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO",
                            bindingResult);

            return "redirect:admin/users-edit";
        }
        //TODO ExceptionHandler
        try {
            this.userService.edit(userEditDTO);
        } catch (UserNotFoundException | RoleNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }
}
