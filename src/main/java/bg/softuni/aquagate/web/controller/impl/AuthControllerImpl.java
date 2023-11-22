package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.web.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;

@Controller
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @Override
    public UserRegistrationDTO initUserRegistrationForm() {
        return new UserRegistrationDTO();
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
        try {
            this.authService.register(userRegistrationDTO);
        } catch (RoleNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", 404);
            redirectAttributes.addFlashAttribute("message", "Role not found!");
            return "redirect:/error";
        }

        return "redirect:/identity/login";
    }

    @Override
    public String login() {
        return "login";
    }
}
