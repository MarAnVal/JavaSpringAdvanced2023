package bg.softuni.aquagateclient.model.dto.view;

import bg.softuni.aquagateclient.model.entity.enumeration.LevelEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileView {

    private Long id;

    private String username;

    private String email;

    private LevelEnum level;
}
