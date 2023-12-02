package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.vallidation.anotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserService userService;
    private String message;

    public UniqueUsernameValidator (UserService userService) {

        this.userService = userService;
    }

    @Override
    public void initialize (UniqueUsername constraintAnnotation) {

        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid (String value, ConstraintValidatorContext context) {

        if (value == null) {

            return true;

        } else {

            final boolean isUnique = userService.findUserByUsername(value).isEmpty();

            if (!isUnique) replaceDefaultConstraintViolation(context, this.message);

            return isUnique;
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