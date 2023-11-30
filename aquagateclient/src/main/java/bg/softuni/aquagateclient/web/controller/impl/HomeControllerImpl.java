package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.web.controller.HomeController;
import bg.softuni.aquagateclient.web.error.TopicNotFoundException;
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
        try {
            TopicView mostCommented = topicService.getMostCommentedTopic();

            ModelAndView model = new ModelAndView("index");
            model.addObject("mostCommented", mostCommented);
            return model;
        } catch (TopicNotFoundException e) {
            ModelAndView model = new ModelAndView("index");
            model.addObject("mostCommented", topicService.getEmptyTopicView());
            return model;
        } catch (RuntimeException e){
            //TODO error handler
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}
