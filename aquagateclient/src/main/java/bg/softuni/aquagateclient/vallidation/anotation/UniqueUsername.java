package bg.softuni.aquagateclient.vallidation.anotation;
import bg.softuni.aquagateclient.vallidation.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username not available!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
