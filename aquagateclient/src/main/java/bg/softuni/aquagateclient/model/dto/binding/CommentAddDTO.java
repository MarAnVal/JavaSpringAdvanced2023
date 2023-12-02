package bg.softuni.aquagateclient.model.dto.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddDTO {

    @NotNull(message = "Field must be filled!")
    @Size(min = 5, max = 250, message = "The length must be between 5 and 250 symbols!")
    private String context;

    private String author;

    private Long topicId;
}
