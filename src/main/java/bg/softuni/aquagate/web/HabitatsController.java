package bg.softuni.aquagate.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/habitats")
public interface HabitatsController {

    @GetMapping("/{habitatName}")
    String habitatsInfo(@PathVariable String habitatName, Model model);
}
