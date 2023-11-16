package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.HomeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeControllerImpl implements HomeController {
    private final TopicService topicService;

    public HomeControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String index(Model model) {
        Topic topic = topicService.getMostCommented();
        model.addAttribute("mostCommented", topic);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/freshwater")
    public String freshwater() {
        return "freshwater";
    }

    @GetMapping("/reef")
    public String reef() {
        return "reef";
    }

    @GetMapping("/blackwater")
    public String blackwater() {
        return "blackwater";
    }

    @GetMapping("/brackish-water")
    public String brackishWater() {
        return "brackish-water";
    }




    @GetMapping("/topics")
    public String topics(Model model) {
        return "topics";
    }

    @GetMapping("/topics/add")
    public String addTopic() {
        return "add-topic";
    }

    @GetMapping("/topics/details")
    public String topicDetails() {
        return "topic-details";
    }

}
