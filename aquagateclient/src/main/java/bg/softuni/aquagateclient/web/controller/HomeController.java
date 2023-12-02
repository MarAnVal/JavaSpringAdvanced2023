package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.interceptor.annotation.PageTitle;
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
    ModelAndView index(Principal principal) throws ObjectNotFoundException, BadRequestException;

    @GetMapping("/about")
    @PageTitle("About")
    @PreAuthorize("isAnonymous()")
    ModelAndView about();
}
