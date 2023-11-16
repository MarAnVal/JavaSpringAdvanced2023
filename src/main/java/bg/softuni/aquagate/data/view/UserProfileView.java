package bg.softuni.aquagate.data.view;

import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import lombok.AllArgsConstructor;
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
