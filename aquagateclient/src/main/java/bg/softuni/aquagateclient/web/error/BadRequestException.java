package bg.softuni.aquagateclient.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad request")
public class BadRequestException extends BaseApplicationException {

    private final int statusCode;

    public BadRequestException() {
        super("Bad request");
        this.statusCode = 400;
    }

    public BadRequestException(String message) {
        super(message);
        this.statusCode = 400;
    }
}
