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

    @Override
    public String about() {
        return "about";
    }

    //TODO refactor habitats pages in new controller and unify the methods and htmls?

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

}
