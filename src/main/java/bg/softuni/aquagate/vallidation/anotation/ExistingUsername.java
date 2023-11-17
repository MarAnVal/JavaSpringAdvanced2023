package bg.softuni.aquagate.vallidation.anotation;

import bg.softuni.aquagate.vallidation.ExistingUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingUsername {
    String message() default "Username not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
