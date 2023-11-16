package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.service.CommentService;
import bg.softuni.aquagate.service.HabitatService;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.TopicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //TODO to decide all approved or users approved topics?
        List<Topic> allApprovedTopics = topicService.getAllApprovedTopics();
        model.addAttribute(allApprovedTopics);
        return "topics";
    }

    @Override
    public String addTopic() {
        //TODO
        return "add-topic";
    }

    @Override
    public String doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            return "redirect:topics/add";
        }

        this.topicService.Add(topicAddDTO);

        return "redirect:/";
    }

    @Override
    public UserEditDTO initAddForm(){
        return new UserEditDTO();
    }

    @Override
    public String topicDetails() {
        //TODO
        return "topic-details";
    }

    //TODO unify search by habitat and methods for it

    @Override
    public String freshwater() {

        return "freshwater";
    }

    @Override
    public String reef() {

        return "reef";
    }

    @Override
    public String blackwater() {

        return "blackwater";
    }

    @Override
    public String brackishWater() {

        return "brackish-water";
    }

    //TODO refactor in adminController?

    @Override
    public String approve() {
        //TODO
        return null;
    }

    @Override
    public String remove() {
        //TODO
        return null;
    }
}
