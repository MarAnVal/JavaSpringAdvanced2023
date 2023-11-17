package bg.softuni.aquagate.data.model;

import bg.softuni.aquagate.vallidation.anotation.ExistingUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {

    @ExistingUsername
    @NotBlank
    @Length(min = 5, max = 20)
    private String username;

    @NotBlank
    private String level;

    @NotBlank
    private String role;
}
