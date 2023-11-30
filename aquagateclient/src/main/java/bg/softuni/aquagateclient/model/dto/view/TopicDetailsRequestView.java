package bg.softuni.aquagateclient.model.dto.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicDetailsRequestView {

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
