package bg.softuni.aquagateclient.data.view;

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

    private String habitat;

    private String level;

    private String description;

    private String author;

    private String videoUrl;

    private String pictures;

    private List<CommentView> comments;
}
