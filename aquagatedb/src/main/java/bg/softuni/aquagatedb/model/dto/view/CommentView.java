package bg.softuni.aquagatedb.model.dto.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentView {

    private Long id;

    private String context;

    private Long authorId;

    private Long topicId;
}
