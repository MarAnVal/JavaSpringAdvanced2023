package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.vallidation.anotation.PasswordConfirmed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, UserRegistrationDTO> {

    @Override
    public boolean isValid(UserRegistrationDTO value, ConstraintValidatorContext context) {
        value.setConfirmed(value.getPassword().equals(value.getConfirmPassword()));
        if (!value.getConfirmed()){
            value.setConfirmPassword(null);
            value.setPassword(null);
        }
        return value.getConfirmed();
    }
}
