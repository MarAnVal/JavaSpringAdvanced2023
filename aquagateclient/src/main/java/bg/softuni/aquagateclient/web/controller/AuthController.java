package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.web.interceptor.annotation.PageTitle;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/identity")
public interface AuthController {

    @GetMapping("/register")
    @PageTitle("Register")
    @PreAuthorize("isAnonymous()")
    ModelAndView register();

    @ModelAttribute("userRegistrationDTO")
    UserRegistrationDTO initUserRegistrationForm();

    @PostMapping("/register")
    @PageTitle("Register")
    @PreAuthorize("isAnonymous()")
    ModelAndView doRegister(@Valid UserRegistrationDTO userRegistrationDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes);

    @GetMapping("/login")
    @PageTitle("Login")
    @PreAuthorize("isAnonymous()")
    ModelAndView login();

    @PostMapping("/login-error")
    @PageTitle("Login")
    @PreAuthorize("isAnonymous()")
    ModelAndView loginError();

    @GetMapping("/logout")
    @PageTitle("Logout")
    @PreAuthorize("isAuthenticated()")
    ModelAndView logout();
}
