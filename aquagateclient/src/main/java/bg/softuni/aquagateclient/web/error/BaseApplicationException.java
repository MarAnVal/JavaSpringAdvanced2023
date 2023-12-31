package bg.softuni.aquagateclient.web.error;

import lombok.Getter;

@Getter
public class BaseApplicationException extends RuntimeException {

    private final int statusCode;

    public BaseApplicationException(String message) {
        super(message);
        statusCode = 400;
    }
}
