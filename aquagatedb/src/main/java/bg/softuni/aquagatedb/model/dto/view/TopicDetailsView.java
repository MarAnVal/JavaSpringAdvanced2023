package bg.softuni.aquagatedb.model.dto.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicDetailsView {

    private Long id;

    private Boolean approved;

    private String name;

    private Long author;

    private String habitat;

    private String level;

    private String description;

    private String videoUrl;

    private String picture;

    private List<CommentView> comments;
}
