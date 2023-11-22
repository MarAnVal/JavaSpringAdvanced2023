package bg.softuni.aquagate.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found!")
public class UserNotFoundException extends Throwable {

    private final int statusCode;

    public UserNotFoundException() {
        super("User not found!");
        this.statusCode = 404;
    }
}
