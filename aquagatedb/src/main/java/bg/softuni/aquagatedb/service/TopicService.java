package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.data.entity.Topic;
import bg.softuni.aquagatedb.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagatedb.data.model.TopicAddDTO;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.web.error.HabitatNotFoundException;
import bg.softuni.aquagatedb.web.error.TopicNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepo topicRepo;
    private final HabitatService habitatService;
    private final PictureService pictureService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicService(TopicRepo topicRepo, HabitatService habitatService,
                        PictureService pictureService,
                        CommentService commentService, ModelMapper modelMapper) {
        this.topicRepo = topicRepo;
        this.habitatService = habitatService;
        this.pictureService = pictureService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    public void addTopic(TopicAddDTO topicAddDTO) throws HabitatNotFoundException {
        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setApproved(false);
        topic.setHabitat(habitatService.findHabitatByName(topicAddDTO.getHabitat()));
        topic.setLevel(LevelEnum.valueOf(topicAddDTO.getLevel()));

        pictureService.add(topicAddDTO.getPictureUrl());

        topic.setAuthorId(topicAddDTO.getUserId());

        topicRepo.save(topic);
    }

    public void approve(Long id) throws TopicNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        topic.setApproved(true);
        topicRepo.save(topic);
    }

    public void remove(Long id) throws TopicNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        commentService.removeByTopicId(topic.getId());

        pictureService.removeByTopicId(topic.getId());

        topicRepo.delete(topic);
    }

    public void save(Topic topic) {
        topicRepo.save(topic);
    }

    public List<Topic> findAllTopics() {
        return topicRepo.findAll();
    }

    public Topic findTopicById(Long id) throws TopicNotFoundException {
        return topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);
    }
}
