package bg.softuni.aquagateclient.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestAddDTO {

    private String context;

    private Long authorId;

    private Long topicId;
}