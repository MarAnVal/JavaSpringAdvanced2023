package bg.softuni.aquagate.vallidation;

import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.vallidation.anotation.PasswordConfirmed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, UserRegistrationDTO> {

    @Override
    public boolean isValid(UserRegistrationDTO value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
