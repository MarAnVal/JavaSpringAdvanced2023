package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.HabitatEnum;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.repository.TopicRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepo topicRepo;
    private final CommentService commentService;
    private final HabitatService habitatService;
    private final CloudService cloudService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicService(TopicRepo topicRepo, CommentService commentService,
                        HabitatService habitatService, CloudService cloudService,
                        ModelMapper modelMapper) {
        this.topicRepo = topicRepo;
        this.commentService = commentService;
        this.habitatService = habitatService;
        this.cloudService = cloudService;
        this.modelMapper = modelMapper;
    }

    public Topic getMostCommented() {
        Topic topic;
        if (topicRepo.getMostCommented().isPresent()) {
            topic = topicRepo.getMostCommented().get();
        } else {
            //TODO remove after adding custom exceptions
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

    public void AddTopic(TopicAddDTO topicAddDTO, UserEntity user) throws IOException {
        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setAuthor(user);

        Habitat habitat = habitatService.findHabitatByName(HabitatEnum.valueOf(topicAddDTO.getHabitat()));
        topic.setHabitat(habitat);

        //TODO refactor to pictureService and use method to get the List
        String pictureUrl = cloudService.uploadImage(topicAddDTO.getPictureUrl());
        Picture picture = new Picture();
        picture.setPictureUrl(pictureUrl);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(picture);
        topic.setPictures(pictures);

        topicRepo.save(topic);
    }

    public List<Topic> getAllApprovedTopics() {
        return topicRepo.findAllByApproved(true);
    }

    public List<Topic> getAllNotApprovedTopics() {
        return topicRepo.findAllByApproved(false);
    }

    public Topic findLatestTopic() {
        return topicRepo.findLatestTopic();
    }
}
