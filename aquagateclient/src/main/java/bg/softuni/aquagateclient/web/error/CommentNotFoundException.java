package bg.softuni.aquagateclient.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comment not found!")
public class CommentNotFoundException extends Throwable {

    private final int statusCode;

    public CommentNotFoundException() {
        super("Comment not found!");
        this.statusCode = 404;
    }
}
