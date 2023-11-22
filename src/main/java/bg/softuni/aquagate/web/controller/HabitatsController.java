package bg.softuni.aquagate.web.controller;

import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/habitats")
public interface HabitatsController {

    @GetMapping("/{habitatName}")
    @PageTitle("Habitats")
    @PreAuthorize("isAnonymous()")
    ModelAndView habitatsInfo(@PathVariable String habitatName);
}
