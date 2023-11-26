package bg.softuni.aquagateclient.vallidation.anotation;

import bg.softuni.aquagateclient.vallidation.PasswordConfirmedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConfirmedValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirmed {
    String message() default "Can not be changed roles of the user!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
