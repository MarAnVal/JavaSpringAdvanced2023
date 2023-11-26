package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.web.interceptor.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequestMapping("users")
public interface UsersController {

    @GetMapping("/profile")
    @PageTitle("Profile")
    @PreAuthorize("isAuthenticated()")
    ModelAndView profile(Principal principal);
}
