package bg.softuni.aquagateclient.model.dto.binding;

import bg.softuni.aquagateclient.vallidation.anotation.IsPictureFile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class TopicAddDTO {

    @NotNull(message = "Field must be filled!")
    @Size(min = 5, max = 20, message = "The length must be between 5 and 20 symbols!")
    private String name;

    @NotNull(message = "Field must be filled!")
    private String habitat;

    @NotNull(message = "Field must be filled!")
    private String level;

    @NotNull
    @Size(min = 5, message = "The length must be at least 5 symbols!")
    private String description;

    @IsPictureFile(contentTypes = {".jpeg/.jpg/.bnp/.png"})
    private MultipartFile pictureFile;

    private String videoUrl;

    private Long author;
}
