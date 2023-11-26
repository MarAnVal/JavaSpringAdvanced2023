package bg.softuni.aquagateclient.data.model;

import bg.softuni.aquagateclient.vallidation.anotation.PasswordConfirmed;
import bg.softuni.aquagateclient.vallidation.anotation.UniqueEmail;
import bg.softuni.aquagateclient.vallidation.anotation.UniqueUsername;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor

@PasswordConfirmed
public class UserRegistrationDTO {
    @UniqueUsername
    @NotBlank
    @Length(min = 5, max = 20)
    private String username;

    @UniqueEmail
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 5, max = 20)
    private String password;

    @NotBlank
    @NotNull(message = "Password dont match confirm password!")
    @Length(min = 5, max = 20)
    private String confirmPassword;

    @AssertTrue
    private Boolean confirmed = true;
}
