package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.data.view.UserProfileView;
import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.web.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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

            return "redirect:/register";
        }

        this.authService.register(userRegistrationDTO);

        return "redirect:/users/login";
    }

    @Override
    public String login() {
        return "login";
    }

    @Override
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        UserProfileView userProfileView = authService.findUserByUsername(username);
        model.addAttribute("user", userProfileView);

        return "profile";
    }

    @Override
    public UserEditDTO initEditForm() {
        return new UserEditDTO();
    }

    @Override
    public String editUser() {

        return "edit";
    }

    @Override
    public String doEditUser(UserEditDTO userEditDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO",
                            bindingResult);

            return "redirect:/edit";
        }

        this.authService.edit(userEditDTO);

        return "redirect:/";
    }
}
