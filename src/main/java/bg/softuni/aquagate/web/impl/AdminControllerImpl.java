package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.service.UserService;
import bg.softuni.aquagate.web.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String pending(Model model) {
        List<TopicsView> allNotApprovedTopics = topicService.mapTopicDetailsViewList(topicService.getAllNotApprovedTopics());

        model.addAttribute("topics", allNotApprovedTopics);

        return "topics";
    }

    @Override
    public String pendingDetails(Long id, Model model) {
        TopicsDetailsView topicsDetailsView = topicService.mapTopicsDetailsView(topicService.findById(id));

        model.addAttribute("topicsDetailsView", topicsDetailsView);

        return "topic-details";
    }

    @Override
    public String remove(Long id) {
        topicService.approve(id);
        return null;
    }

    @Override
    public String approve(Long id) {
        topicService.remove(id);
        return null;
    }

    //TODO ONLY admin could change roles
    @Override
    public UserEditDTO initEditUserForm() {
        return new UserEditDTO();
    }

    @Override
    public String editUser() {
        return "edit";
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

        this.userService.edit(userEditDTO);

        return "redirect:/";
    }
}
