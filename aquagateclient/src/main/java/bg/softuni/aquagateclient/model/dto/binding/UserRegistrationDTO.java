package bg.softuni.aquagateclient.model.dto.binding;

import bg.softuni.aquagateclient.vallidation.anotation.PasswordConfirmed;
import bg.softuni.aquagateclient.vallidation.anotation.UniqueEmail;
import bg.softuni.aquagateclient.vallidation.anotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@PasswordConfirmed
public class UserRegistrationDTO {

    @UniqueUsername
    @NotNull(message = "Field must be filled!")
    private String username;

    @UniqueEmail
    @Email(message = "The email must be valid one!")
    @NotNull(message = "Field must be filled!")
    private String email;

    @NotNull(message = "Field must be filled!")
    private String password;

    @NotNull(message = "Field must be filled!")
    @Size(min = 5, max = 20, message = "The length must be between 5 and 20 symbols!")
    private String confirmPassword;
}
