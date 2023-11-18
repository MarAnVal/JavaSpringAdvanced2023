package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.view.UserProfileView;
import bg.softuni.aquagate.service.UserService;
import bg.softuni.aquagate.web.UsersController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class UsersControllerImpl implements UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersControllerImpl(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String profile(Principal principal, Model model) {
        //TODO debug after adding spring security
        String username = principal.getName();
        UserProfileView userProfileView = modelMapper
                .map(userService.findUserByUsername(username), UserProfileView.class);
        model.addAttribute("user", userProfileView);

        return "profile";
    }
}
