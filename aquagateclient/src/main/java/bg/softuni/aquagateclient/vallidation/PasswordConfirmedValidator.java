package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.vallidation.anotation.PasswordConfirmed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, UserRegistrationDTO> {
    private String message;

    @Override
    public boolean isValid(UserRegistrationDTO value, ConstraintValidatorContext context) {

        final String password = value.getPassword();
        final String confirmPassword = value.getConfirmPassword();

        if (password == null && confirmPassword == null) {

            return true;
        } else {

            boolean passwordConfirmed = password != null && password.equals(confirmPassword);

            if (!passwordConfirmed) {

                HibernateConstraintValidatorContext hibernateContext =
                        context.unwrap(HibernateConstraintValidatorContext.class);

                hibernateContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }

            return passwordConfirmed;
        }
    }

    @Override
    public void initialize(PasswordConfirmed constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }
}
