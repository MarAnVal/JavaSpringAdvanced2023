package bg.softuni.aquagate.web.controller;

import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequestMapping("/")
public interface HomeController {
    @GetMapping("/")
    @PageTitle("Home")
    @PreAuthorize("isAnonymous()")
    ModelAndView index(Principal principal);

    @GetMapping("/about")
    @PageTitle("About")
    @PreAuthorize("isAnonymous()")
    ModelAndView about();
}
