package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.view.HabitatView;
import bg.softuni.aquagate.service.HabitatService;
import bg.softuni.aquagate.web.HabitatsController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
    public String habitatsInfo(String habitatName, Model model) {
        HabitatView habitatView = mapHabitatView(habitatService.findHabitatByName(habitatName));
        model.addAttribute("habitatView", habitatView);
        return "habitat";
    }

    private HabitatView mapHabitatView(Habitat habitat) {
        HabitatView habitatView = modelMapper.map(habitat, HabitatView.class);
        habitatView.setName(habitat.getName().toString());

        return habitatView;
    }
}
