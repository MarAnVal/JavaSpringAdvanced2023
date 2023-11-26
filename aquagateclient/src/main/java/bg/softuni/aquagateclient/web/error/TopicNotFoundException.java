package bg.softuni.aquagateclient.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Topic not found!")
public class TopicNotFoundException extends Throwable {

    private final int statusCode;

    public TopicNotFoundException() {
        super("Topic not found!");
        this.statusCode = 404;
    }
}
