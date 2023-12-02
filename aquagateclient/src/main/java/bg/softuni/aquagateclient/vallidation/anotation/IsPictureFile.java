package bg.softuni.aquagateclient.vallidation.anotation;

import bg.softuni.aquagateclient.vallidation.IsPictureFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsPictureFileValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPictureFile {

    long size() default 5 * 1024 * 1024;

    String[] contentTypes();

    String message() default "File must be of type .jpg, .jpeg, .bnp or .png!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
