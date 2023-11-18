package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.web.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserRegistrationDTO initRegistrationForm() {
        return new UserRegistrationDTO();
    }

    @Override
    public String register() {
        return "register";
    }

    @Override
    public String doRegister(UserRegistrationDTO userRegistrationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO",
                            bindingResult);

            return "redirect:/identity/register";
        }

        this.authService.register(userRegistrationDTO);

        return "redirect:/identity/login";
    }

    @Override
    public String login() {
        //TODO after adding spring security
        //TODO check how works with it password decode and match
        return "login";
    }

    //TODO logout
}
