package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.CommentAddDTO;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.TopicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class TopicControllerImpl implements TopicController {
    private final TopicService topicService;


    @Autowired
    public TopicControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String topics(Model model) {
        List<TopicsView> allApprovedTopics = topicService.mapTopicDetailsViewList(topicService.getAllApprovedTopics());

        model.addAttribute("topics", allApprovedTopics);

        return "topics";
    }

    @Override
    public TopicAddDTO initAddTopicForm() {
        return new TopicAddDTO();
    }

    @Override
    public String addTopic() {
        return "add-topic";
    }

    @Override
    public String doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            return "redirect:topics/add";
        }

        topicAddDTO.setUserName(principal.getName());
        this.topicService.AddTopic(topicAddDTO);

        return "redirect:/";
    }

    @Override
    public String topicDetails(Long id, Model model) {
        TopicsDetailsView topicsDetailsView = topicService.mapTopicsDetailsView(topicService.findById(id));

        model.addAttribute("topicsDetailsView", topicsDetailsView);

        return "topic-details";
    }

    @Override
    public String latestTopic() {
        return "redirect:/topics/details/" + topicService.findLatestTopic().getId();
    }

    @Override
    public String mostCommented() {
        return "redirect:/topics/details/" + topicService.getMostCommented().getId();
    }

    @Override
    public String doCommentAdd(Long id, CommentAddDTO commentAddDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Principal principal) {
        //TODO implement
        return null;
    }

    @Override
    public String doPictureAdd(Long id, PictureAddDTO commentAddDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Principal principal) {
        //TODO implement
        return null;
    }
}
