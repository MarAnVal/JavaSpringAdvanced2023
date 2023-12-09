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
    String message() default "Email already occupied!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
