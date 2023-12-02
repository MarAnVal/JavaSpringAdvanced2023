package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.vallidation.anotation.ExistingUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {
    private final UserRepo userRepo;
    private String message;

    public ExistingUsernameValidator (UserRepo userRepo) {

        this.userRepo = userRepo;
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

            final boolean isPresent = userRepo.findUserByUsername(value).isPresent();

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
