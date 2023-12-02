package bg.softuni.aquagateclient.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found!")
public class ObjectNotFoundException extends BaseApplicationException {

    private final int statusCode;

    public ObjectNotFoundException() {
        super("Not found!");
        this.statusCode = 404;
    }

    public ObjectNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }
}
