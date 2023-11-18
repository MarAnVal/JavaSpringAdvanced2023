package bg.softuni.aquagate.data.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicsDetailsView {

    private Long id;

    private Boolean approved;

    private String name;

    private String habitat;

    private String level;

    private String description;

    private String author;

    private String videoUrl;

    private List<PictureView> pictures;

    private List<CommentView> comments;
}
