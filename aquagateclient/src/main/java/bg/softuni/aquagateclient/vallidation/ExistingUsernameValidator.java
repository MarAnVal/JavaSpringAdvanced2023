package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.vallidation.anotation.ExistingUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {
    private final UserService userService;
    private String message;

    public ExistingUsernameValidator (UserService userService) {

        this.userService = userService;
    }

    @Override
    public void initialize(ExistingUsername constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid (String value, ConstraintValidatorContext context) {

        if (value == null) {

            return true;

        } else {

            final boolean isPresent = userService.findUserByUsername(value).isPresent();

            if (!isPresent) replaceDefaultConstraintViolation(context, this.message);

            return isPresent;
        }
    }

    private void replaceDefaultConstraintViolation (ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

}
