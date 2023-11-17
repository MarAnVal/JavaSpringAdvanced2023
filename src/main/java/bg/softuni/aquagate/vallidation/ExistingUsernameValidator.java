package bg.softuni.aquagate.vallidation;

import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.vallidation.anotation.ExistingUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {
    private final AuthService authService;

    public ExistingUsernameValidator(AuthService userService) {
        this.authService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return authService.findUserByUsername(value) != null;
    }
}
