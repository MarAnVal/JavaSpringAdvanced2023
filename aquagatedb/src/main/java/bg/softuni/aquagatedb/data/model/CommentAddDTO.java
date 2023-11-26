package bg.softuni.aquagatedb.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddDTO {

    @NotBlank
    @Length(min = 5, max = 250)
    private String context;

    @NotBlank
    private Long authorId;

    @NotBlank
    private Long topicId;
}
