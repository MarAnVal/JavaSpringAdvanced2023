package bg.softuni.aquagateclient.data.view;

import bg.softuni.aquagateclient.data.entity.enumeration.LevelEnum;
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
