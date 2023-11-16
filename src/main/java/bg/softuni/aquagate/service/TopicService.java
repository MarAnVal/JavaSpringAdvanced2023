package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepo topicRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    public Topic getMostCommented() {
        Topic topic;
        if(topicRepo.getMostCommented().isPresent()) {
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

    public void register(TopicAddDTO topicAddDTO) {

    }
}
