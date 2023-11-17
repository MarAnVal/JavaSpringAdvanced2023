package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.data.view.UserProfileView;
import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.web.AuthController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    private final ModelMapper modelMapper;

    @Autowired
    public AuthControllerImpl(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
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
        //TODO config modelMapper to encode password and password match with confirmPassword!
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO",
                            bindingResult);

            return "redirect:/users/register";
        }

        this.authService.register(userRegistrationDTO);

        return "redirect:/users/login";
    }

    @Override
    public String login() {
        //TODO after adding spring security
        return "login";
    }

    @Override
    public String profile(Principal principal, Model model) {
        //TODO debug after adding spring security
        String username = principal.getName();
        UserProfileView userProfileView = modelMapper
                .map(authService.findUserByUsername(username), UserProfileView.class);
        model.addAttribute("user", userProfileView);

        return "profile";
    }

    //TODO refactor in adminController??

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

            return "redirect:/users/edit";
        }

        this.authService.edit(userEditDTO);

        return "redirect:/";
    }
}
