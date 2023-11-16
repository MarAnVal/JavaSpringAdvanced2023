package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepo topicRepo;
    private final CommentService commentService;
    private final HabitatService habitatService;

    @Autowired
    public TopicService(TopicRepo topicRepo,
                        CommentService commentService,
                        HabitatService habitatService) {
        this.topicRepo = topicRepo;
        this.commentService = commentService;
        this.habitatService = habitatService;
    }

    public Topic getMostCommented() {
        Topic topic;
        if (topicRepo.getMostCommented().isPresent()) {
            topic = topicRepo.getMostCommented().get();
        } else {
            topic = new Topic();
            topic.setName("No topics added");
            List<Picture> pictures = new ArrayList<>();
            Picture picture = new Picture();
            picture.setPictureUrl("/images/picture-not-found.png");
            pictures.add(picture);
            topic.setPictures(pictures);
        }
        return topic;
    }

    public void Add(TopicAddDTO topicAddDTO) {
        //TODO
    }

    public List<Topic> getAllApprovedTopics() {
        //TODO decide if needed or getMyTopics()
        return null;
    }
}
