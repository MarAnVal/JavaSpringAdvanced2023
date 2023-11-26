package bg.softuni.aquagateclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTopicsConfiguration {

    @Value("${}")
    private String host;

    @Value("${}")
    private String topicsShema;

    @Value("${}")
    private String topicsAddPath;

    @Value("${}")
    private String topicsDetailsPath;

    @Value("${}")
    private String topicsRemovePath;

    @Value("${}")
    private String topicsApprovePath;

    public String topicsAllUrlSource(){
        return host+topicsShema;
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
