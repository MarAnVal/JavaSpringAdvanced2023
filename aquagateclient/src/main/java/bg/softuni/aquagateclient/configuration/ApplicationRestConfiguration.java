package bg.softuni.aquagateclient.configuration;

import bg.softuni.aquagateclient.service.rest.util.CommentRestUtil;
import bg.softuni.aquagateclient.service.rest.util.TopicRestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationRestConfiguration {

    @Value("${api.configuration.host}")
    private String host;

    @Value("${api.configuration.topics-schema}")
    private String topicsShema;

    @Value("${api.configuration.topics-all-path}")
    private String topicsAllPath;

    @Value("${api.configuration.topics-add-path}")
    private String topicsAddPath;

    @Value("${api.configuration.topics-details-path}")
    private String topicsDetailsPath;

    @Value("${api.configuration.topics-remove-path}")
    private String topicsRemovePath;

    @Value("${api.configuration.topics-approve-path}")
    private String topicsApprovePath;

    @Value("${api.configuration.comments-schema}")
    private String commentsShema;

    @Value("${api.configuration.comments-add-path}")
    private String commentsAddPath;

    @Bean
    public CommentRestUtil commentRestUtil() {
        return new CommentRestUtil(host + commentsShema + commentsAddPath);
    }

    @Bean
    public TopicRestUtil topicRestUtil() {
        return new TopicRestUtil(host + topicsShema + topicsAllPath,
                host + topicsShema + topicsAddPath,
                host + topicsShema + topicsDetailsPath,
                host + topicsShema + topicsRemovePath,
                host + topicsShema + topicsApprovePath);
    }
}
