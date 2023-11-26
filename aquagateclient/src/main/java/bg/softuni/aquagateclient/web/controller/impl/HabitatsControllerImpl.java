package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.web.controller.HabitatsController;
import bg.softuni.aquagateclient.web.error.HabitatNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HabitatsControllerImpl implements HabitatsController {

    @Override
    public ModelAndView habitatsInfo(String habitatName) {
        switch (habitatName){
            case "freshwater":
            case "blackwater":
            case "brackish-water":
            case "reef":
                ModelAndView model = new ModelAndView("habitat");
                model.addObject("habitatView", habitatName);
                return model;
            default:
             ModelAndView modelError = new ModelAndView("error");
             modelError.addObject(new HabitatNotFoundException());
             return modelError;
        }
    }
}
