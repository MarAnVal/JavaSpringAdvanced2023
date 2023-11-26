package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationTopicsConfiguration;
import bg.softuni.aquagateclient.data.model.TopicAddDTO;
import bg.softuni.aquagateclient.data.view.TopicDetailsView;
import bg.softuni.aquagateclient.data.view.TopicView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final ApplicationTopicsConfiguration applicationTopicsConfiguration;

    public TopicService(ApplicationTopicsConfiguration applicationTopicsConfiguration) {
        this.applicationTopicsConfiguration = applicationTopicsConfiguration;
    }


    public List<TopicView> getAllTopics() {
        String url = applicationTopicsConfiguration.topicsAllUrlSource();
        //TODO
        return null;
    }

    public void remove(Long id) {
        String url = applicationTopicsConfiguration.topicRemoveUrlSource() + "/" + id;
        //TODO
    }

    public List<TopicView> getAllPendingTopics() {
        String url = applicationTopicsConfiguration.topicAddUrlSource();
        //TODO
        return null;
    }

    public void approve(Long id) {
        String url = applicationTopicsConfiguration.topicApproveUrlSource() + "/" + id;
        //TODO
    }

    public List<TopicView> getAllApprovedTopics() {
        String url = applicationTopicsConfiguration.topicsAllUrlSource();
        //TODO
        return null;
    }

    public void addTopic(TopicAddDTO topicAddDTO) {
        String url = applicationTopicsConfiguration.topicAddUrlSource();
        //TODO
    }

    public TopicDetailsView getTopicDetails(Long id) {
        String url = applicationTopicsConfiguration.topicDetailsUrlSource() + "/" + id;
        //TODO
        return null;
    }

    public TopicView getLatestTopic() {
        List<TopicView> allTopics = getAllTopics();
        //TODO
        return null;
    }

    public TopicView getMostCommentedTopic() {
        List<TopicView> allTopics = getAllTopics();
        //TODO
        return null;
    }

    public List<TopicView> getAllTopicsByUserId(Long id) {
        List<TopicView> allTopics = getAllTopics();
        //TODO
        return null;
    }
}
