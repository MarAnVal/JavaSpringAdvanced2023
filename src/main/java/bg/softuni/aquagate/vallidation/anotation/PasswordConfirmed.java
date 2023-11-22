package bg.softuni.aquagate.vallidation.anotation;

import bg.softuni.aquagate.vallidation.PasswordConfirmedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConfirmedValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmed {
    String message() default "Can not be changed roles of the user!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
