package bg.softuni.aquagateclient.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicRequestAddDTO {

    private String name;

    private String habitat;

    private String level;

    private String description;

    private String pictureUrl;

    private String videoUrl;

    private Long author;
}
