package bg.softuni.aquagate.web.controller;

import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
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
    String doRegister(@Valid UserRegistrationDTO userRegistrationDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes);

    @GetMapping("/login")
    @PageTitle("Login")
    @PreAuthorize("isAnonymous()")
    String login();
}
