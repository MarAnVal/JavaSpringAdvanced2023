package bg.softuni.aquagatedb.data.model;

import bg.softuni.aquagatedb.vallidation.anotation.IsPictureFile;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class TopicAddDTO {

    @NotBlank
    @Length(min = 5, max = 20)
    private String name;

    @NotBlank
    private String habitat;

    @NotBlank
    private String level;

    @NotBlank
    @Length(min = 5)
    private String description;

    @IsPictureFile
    private String pictureUrl;

    @Length(min = 11, max = 11)
    private String videoUrl;

    @NotBlank
    private Long userId;
}
