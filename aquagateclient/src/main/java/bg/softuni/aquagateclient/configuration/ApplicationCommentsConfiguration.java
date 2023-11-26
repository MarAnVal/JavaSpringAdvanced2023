package bg.softuni.aquagateclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationCommentsConfiguration {
    //TODO add values
    @Value("${}")
    private String host;

    @Value("${}")
    private String commentsShema;

    @Value("${}")
    private String commentsAddPath;

    public String commentsAddUrlSource() {
        return host + commentsShema + commentsAddPath;
    }
}
