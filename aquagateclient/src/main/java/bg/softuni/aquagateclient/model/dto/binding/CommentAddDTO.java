package bg.softuni.aquagateclient.model.dto.binding;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddDTO {

    @NotNull
    @Length(min = 5, max = 250)
    private String context;

    @NotNull
    private Long authorId;

    @NotNull
    private Long topicId;
}
