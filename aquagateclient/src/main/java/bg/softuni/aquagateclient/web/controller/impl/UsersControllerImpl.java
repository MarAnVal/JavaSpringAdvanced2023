package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.view.UserProfileView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.controller.UsersController;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ModelAndView profile(Principal principal) throws ObjectNotFoundException {
            UserEntity userByUsername = userService.findUserByUsername(principal.getName());
            UserProfileView userProfileView = modelMapper
                    .map(userByUsername, UserProfileView.class);

            ModelAndView model = new ModelAndView("profile");
            model.addObject("user", userProfileView);

            return model;
    }

    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }
}
