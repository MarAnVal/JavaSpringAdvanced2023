package bg.softuni.aquagateclient.model.dto.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentView {

    private String context;

    private String author;
}