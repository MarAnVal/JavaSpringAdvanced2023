package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.data.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.web.controller.HomeController;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeControllerImpl implements HomeController {
    private final TopicService topicService;

    public HomeControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public ModelAndView index(Principal principal) {

        TopicView mostCommented = topicService.getMostCommentedTopic();

        ModelAndView model = new ModelAndView("index");
        model.addObject("mostCommented", mostCommented);
        return model;
    }

    @Override
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}
