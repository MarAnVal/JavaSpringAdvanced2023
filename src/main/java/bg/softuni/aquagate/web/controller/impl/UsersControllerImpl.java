package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.view.UserProfileView;
import bg.softuni.aquagate.service.UserService;
import bg.softuni.aquagate.web.controller.UsersController;
import bg.softuni.aquagate.web.error.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView profile(Principal principal) {
        String username = principal.getName();
        UserEntity userByUsername;
        try {
            userByUsername = userService.findUserByUsername(username);
        } catch (UserNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }
        UserProfileView userProfileView = modelMapper
                .map(userByUsername, UserProfileView.class);

        ModelAndView model = new ModelAndView("profile");
        model.addObject("user", userProfileView);

        return model;
    }
}
