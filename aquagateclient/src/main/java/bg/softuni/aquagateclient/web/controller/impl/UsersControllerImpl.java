package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.dto.view.UserProfileView;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.UsersController;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
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
        //TODO ExceptionHandler
        try {
            userByUsername = userService.findUserByUsername(username);
        } catch (UserNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", 404);
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
