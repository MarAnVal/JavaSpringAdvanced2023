package bg.softuni.aquagate.vallidation;

import bg.softuni.aquagate.service.AuthService;
import bg.softuni.aquagate.vallidation.anotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AuthService authService;

    public UniqueEmailValidator(AuthService userService) {
        this.authService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return authService.findUserByEmail(value) == null;
    }
}