package bg.softuni.aquagatedb.model.dto.binding;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    private String pictureUrl;

    private String videoUrl;

    @NotNull
    private Long author;
}
