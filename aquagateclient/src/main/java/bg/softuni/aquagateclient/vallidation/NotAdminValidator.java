package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.vallidation.anotation.NotAdmin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {
    private final UserService userService;
    private String message;

    public NotAdminValidator(UserService userService) {

        this.userService = userService;
    }

    @Override
    public void initialize(NotAdmin constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || userService.findUserByUsername(value).isEmpty()) {

            return true;

        } else {

            RoleEnum admin = userService.findUserByUsername(value).get()
                    .getRoles().stream()
                    .map(Role::getName)
                    .filter(e -> e.equals(RoleEnum.ADMIN))
                    .findFirst().orElse(null);

            if (admin != null) replaceDefaultConstraintViolation(context, this.message);

            return admin == null;
        }
    }

    private void replaceDefaultConstraintViolation(ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
