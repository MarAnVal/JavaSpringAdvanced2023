package bg.softuni.aquagate.vallidation;

import bg.softuni.aquagate.repository.UserRepo;
import bg.softuni.aquagate.vallidation.anotation.ExistingUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistingUsernameValidator implements ConstraintValidator<ExistingUsername, String> {
    private final UserRepo userRepo;

    @Autowired
    public ExistingUsernameValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepo.findUserByUsername(value).isPresent();
    }

}
