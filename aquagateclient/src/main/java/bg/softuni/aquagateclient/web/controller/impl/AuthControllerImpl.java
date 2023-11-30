package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;

@Controller
public class AuthControllerImpl implements AuthController {

    private final UserService userService;

    @Autowired
    public AuthControllerImpl(UserService userService) {
        this.userService = userService;
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

        //TODO ExceptionHandler
        try {
            this.userService.registerUser(userRegistrationDTO);
        } catch (RoleNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/identity/login";
    }

    @Override
    public String login() {
        return "login";
    }

    @Override
    public ModelAndView loginError() {
        ModelAndView model = new ModelAndView("login");
        model.addObject("badCredentials", true);
        return model;
    }

    @Override
    public String logout() {
        return "logout";
    }
}
