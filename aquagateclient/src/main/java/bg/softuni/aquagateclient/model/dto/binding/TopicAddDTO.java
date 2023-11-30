package bg.softuni.aquagateclient.model.dto.binding;

import bg.softuni.aquagateclient.vallidation.anotation.IsPictureFile;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class TopicAddDTO {

    @NotNull
    @Length(min = 5, max = 20)
    private String name;

    @NotNull
    private String habitat;

    @NotNull
    private String level;

    @NotNull
    @Length(min = 5)
    private String description;

    @IsPictureFile
    private MultipartFile pictureFile;

    private String videoUrl;

    private Long author;

    private Boolean pictureError;
}
