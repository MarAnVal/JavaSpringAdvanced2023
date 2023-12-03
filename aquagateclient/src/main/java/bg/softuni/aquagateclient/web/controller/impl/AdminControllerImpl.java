package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.UserEditDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.AdminController;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView pending() throws BadRequestException {

            List<TopicView> allNotApprovedTopicsViews = topicService.getAllNotApprovedTopics();

            ModelAndView model = new ModelAndView("topics");
            model.addObject("topics", allNotApprovedTopicsViews);

            return model;
    }

    @Override
    public ModelAndView pendingDetails(Long id) throws BadRequestException, ObjectNotFoundException {
            TopicDetailsView topicDetails = topicService.getTopicDetails(id);

            ModelAndView model = new ModelAndView("topic-detail" + id);
            model.addObject("topicsDetailsView", topicDetails);

            return model;
    }

    @Override
    public ModelAndView remove(Long id) throws BadRequestException {
            topicService.removeTopic(id);
            return new ModelAndView("redirect:/admin/pending");
    }

    @Override
    public ModelAndView approve(Long id) throws BadRequestException {
            topicService.approveTopic(id);

            return new ModelAndView("redirect:/admin/pending");
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
                             RedirectAttributes redirectAttributes) throws ObjectNotFoundException {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO",
                            bindingResult);

            modelAndView.setViewName("redirect:admin/users-editUser");
        } else {
                this.userService.editUser(userEditDTO);
                modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
