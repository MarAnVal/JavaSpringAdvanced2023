package bg.softuni.aquagatedb.vallidation;

import bg.softuni.aquagatedb.vallidation.anotation.IsPictureFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IsPictureFileValidator implements ConstraintValidator<IsPictureFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        String name = value.getName();
        Pattern pattern = Pattern.compile(".jpeg$|.jpg$|.bnp$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
