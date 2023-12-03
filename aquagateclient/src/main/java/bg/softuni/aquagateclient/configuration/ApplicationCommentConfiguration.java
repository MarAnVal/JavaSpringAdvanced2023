package bg.softuni.aquagateclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationCommentConfiguration {

    @Value("${api.configuration.host}")
    private String host;

    @Value("${api.configuration.comments-schema}")
    private String commentsShema;

    @Value("${api.configuration.comments-add-path}")
    private String commentsAddPath;

    public String commentAddUrlSource() {
        return host + commentsShema + commentsAddPath;
    }
}
