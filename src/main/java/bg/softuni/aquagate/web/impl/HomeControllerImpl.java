package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.HomeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HomeControllerImpl implements HomeController {
    private final TopicService topicService;

    public HomeControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String index(Model model) {
        Topic topic = topicService.getMostCommented();
        //TODO add in model last 2 added pictures;
        // get pictures only from approved topics
        model.addAttribute("mostCommented", topic);
        return "index";
    }

    @Override
    public String about() {
        return "about";
    }

    //TODO think about unify pages and the methods
    // (refactor the description from htmls to filed description in class Habitat)
    // and call it from there with parameter
    // maybe refactor in habitatController?

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
