package bg.softuni.aquagate.vallidation;

import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.vallidation.anotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final AuthService authService;

    public UniqueUsernameValidator(AuthService userService) {
        this.authService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.authService.findUserByUsername(value) == null;
    }
}