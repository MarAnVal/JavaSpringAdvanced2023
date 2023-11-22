package bg.softuni.aquagate.data.model;

import bg.softuni.aquagate.vallidation.anotation.IsPictureFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PictureAddDTO {

    @NotBlank
    @IsPictureFile
    private MultipartFile pictureFile;

    private Long topicId;
}
