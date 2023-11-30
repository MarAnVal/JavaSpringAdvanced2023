package bg.softuni.aquagateclient.model.dto.binding;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class TopicRequestAddDTO {

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

    private String pictureUrl;

    private String videoUrl;

    private Long author;
}
