package bg.softuni.aquagateclient.vallidation.anotation;

import bg.softuni.aquagateclient.vallidation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email already occupied!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
