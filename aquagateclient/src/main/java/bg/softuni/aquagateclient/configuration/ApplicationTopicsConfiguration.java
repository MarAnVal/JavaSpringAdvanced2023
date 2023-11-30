package bg.softuni.aquagateclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTopicsConfiguration {

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

    public String topicsAllUrlSource(){
        return host+topicsShema+topicsAllPath;
    }

    public String topicAddUrlSource(){
        return host+topicsShema+topicsAddPath;
    }

    public String topicDetailsUrlSource(){
        return host+topicsShema+topicsDetailsPath;
    }

    public String topicRemoveUrlSource(){
        return host+topicsShema+topicsRemovePath;
    }

    public String topicApproveUrlSource(){
        return host+topicsShema+topicsApprovePath;
    }
}
