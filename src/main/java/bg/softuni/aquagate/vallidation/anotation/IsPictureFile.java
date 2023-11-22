package bg.softuni.aquagate.vallidation.anotation;

import bg.softuni.aquagate.vallidation.IsPictureFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsPictureFileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPictureFile {
    String message() default "Picture file have to be jpeg, jpg or bnp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
