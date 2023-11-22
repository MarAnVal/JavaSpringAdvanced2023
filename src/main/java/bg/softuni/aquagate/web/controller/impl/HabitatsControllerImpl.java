package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.view.HabitatView;
import bg.softuni.aquagate.service.HabitatService;
import bg.softuni.aquagate.web.controller.HabitatsController;
import bg.softuni.aquagate.web.error.HabitatNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HabitatsControllerImpl implements HabitatsController {

    private final HabitatService habitatService;
    private final ModelMapper modelMapper;

    @Autowired
    public HabitatsControllerImpl(HabitatService habitatService, ModelMapper modelMapper) {
        this.habitatService = habitatService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelAndView habitatsInfo(String habitatName) {
        Habitat habitat;
        try {
            habitat = habitatService.findByTitle(habitatName);
        } catch (HabitatNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }
        HabitatView habitatView = mapHabitatView(habitat);

        ModelAndView model = new ModelAndView("habitat");
        model.addObject("habitatView", habitatView);
        return model;
    }

    private HabitatView mapHabitatView(Habitat habitat) {
        HabitatView habitatView = modelMapper.map(habitat, HabitatView.class);
        habitatView.setName(habitat.getName().toString());

        return habitatView;
    }
}
