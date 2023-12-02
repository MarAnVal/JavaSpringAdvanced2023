package bg.softuni.aquagateclient.model.dto.binding;

import bg.softuni.aquagateclient.vallidation.anotation.ExistingUsername;
import bg.softuni.aquagateclient.vallidation.anotation.NotAdmin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {

    @ExistingUsername
    @NotAdmin
    @NotNull(message = "Field must be filled!")
    @Size(min = 5, max = 20, message = "The length must be between 5 and 20 symbols!")
    private String username;

    @NotNull(message = "Field must be filled!")
    private String level;

    @NotNull(message = "Field must be filled!")
    private String role;
}
