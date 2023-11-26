package bg.softuni.aquagateclient.vallidation.anotation;

import bg.softuni.aquagateclient.vallidation.NotAdminValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotAdminValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotAdmin {
    String message() default "Can not be changed roles of the user!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
