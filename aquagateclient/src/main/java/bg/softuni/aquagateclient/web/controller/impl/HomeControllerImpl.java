package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.TopicService;
import bg.softuni.aquagateclient.web.controller.HomeController;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeControllerImpl implements HomeController {
    private final TopicService topicService;

    public HomeControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public ModelAndView index() throws BadRequestException, ObjectNotFoundException {

            TopicView mostCommented = topicService.getMostCommentedTopic();

            ModelAndView model = new ModelAndView("index");
            model.addObject("mostCommented", mostCommented);
            return model;
    }

    @Override
    public ModelAndView about() {
        return new ModelAndView("about");
    }


    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
