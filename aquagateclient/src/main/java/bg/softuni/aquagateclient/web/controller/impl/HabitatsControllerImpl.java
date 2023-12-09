package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.web.controller.HabitatsController;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HabitatsControllerImpl implements HabitatsController {

    @Override
    public ModelAndView habitatsInfo(String habitatName) throws ObjectNotFoundException {
        switch (habitatName){
            case "freshwater":
            case "blackwater":
            case "brackish-water":
            case "reef":
                ModelAndView model = new ModelAndView("habitat");
                model.addObject("habitatView", habitatName);
                return model;
            default:
                throw new ObjectNotFoundException("Habitat not found!");
        }
    }

    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
