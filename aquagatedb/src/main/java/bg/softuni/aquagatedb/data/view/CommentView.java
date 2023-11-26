package bg.softuni.aquagatedb.data.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentView {

    private String context;

    private Long authorId;
}
