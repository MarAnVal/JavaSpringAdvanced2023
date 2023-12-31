package bg.softuni.aquagateclient.vallidation;

import bg.softuni.aquagateclient.vallidation.anotation.IsPictureFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class IsPictureFileValidator implements ConstraintValidator<IsPictureFile, MultipartFile> {
    private List<String> contentTypes;

    private long size;

    @Override
    public void initialize(IsPictureFile constraintAnnotation) {
        this.size = constraintAnnotation.size();
        this.contentTypes = Arrays.stream(constraintAnnotation.contentTypes()).toList();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty()) {
            return true;
        }
        String errorMsg = getErrorMsg(file);

        if (!errorMsg.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMsg)
                    .addConstraintViolation();

            return false;
        }

        return true;

    }

    private String getErrorMsg(MultipartFile file) {
        if (file.isEmpty()) {
            return "File must be provided";
        }

        if (file.getSize() > size) {
            return "Exceeded file size. Max size: " + size;
        }

        if (!contentTypes.contains(file.getContentType())) {
            return "Invalid file extension. Supported files: " + String.join(", ", contentTypes);
        }

        return "";
    }
}
