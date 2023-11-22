package bg.softuni.aquagate.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Picture not fund!")
public class PictureNotFoundException extends Throwable {

    private final int statusCode;

    public PictureNotFoundException() {
        super("Picture not fund!");
        this.statusCode = 404;
    }
}
