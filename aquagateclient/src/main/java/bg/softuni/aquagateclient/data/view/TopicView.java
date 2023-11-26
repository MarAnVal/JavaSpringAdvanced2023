package bg.softuni.aquagateclient.data.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicView {

    private Long id;

    private String name;

    private String description;

    private String pictureUrl;

    private Boolean approved;
}
