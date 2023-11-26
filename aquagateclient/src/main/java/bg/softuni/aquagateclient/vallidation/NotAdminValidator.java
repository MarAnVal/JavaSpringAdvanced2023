package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.data.entity.Role;
import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.vallidation.anotation.NotAdmin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {
    private final UserRepo userRepo;

    @Autowired
    public NotAdminValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userRepo.findUserByUsername(value).isPresent()) {
            List<Role> roles = userRepo.findUserByUsername(value).get().getRoles();

            //TODO check roles in debug
            for (Role role : roles) {
                if (role.getName().toString().equals("ADMIN")) {
                    return false;
                }
            }
        }
        return true;
    }
}
