package bg.softuni.aquagateclient.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Habitat not found!")
public class HabitatNotFoundException extends Throwable {

    private final int statusCode;

    public HabitatNotFoundException() {
        super("Habitat not found!");
        this.statusCode = 404;
    }
}
