package bg.softuni.aquagate.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddDTO {
    @NotBlank
    private String context;

    private String AuthorUsername;

    private Long topicId;
}
