package bg.softuni.aquagatedb.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found!")
public class ObjectNotFoundException extends Throwable {

    private final int statusCode;

    public ObjectNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }
}
