package bg.softuni.aquagateclient.model.dto.binding;

import bg.softuni.aquagateclient.vallidation.anotation.ExistingUsername;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {

    @ExistingUsername
    @NotNull
    @Length(min = 5, max = 20)
    private String username;

    @NotNull
    private String level;

    @NotNull
    private String role;
}
