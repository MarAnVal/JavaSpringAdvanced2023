package bg.softuni.aquagate.data.model;

import bg.softuni.aquagate.vallidation.anotation.UniqueEmail;
import bg.softuni.aquagate.vallidation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {

    private Long id;

    @UniqueUsername
    @NotBlank
    @Length(min = 5, max = 20)
    private String username;

    @UniqueEmail
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String level;

    @NotBlank
    private String role;
}
