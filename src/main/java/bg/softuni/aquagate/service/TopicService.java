package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Comment;
import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.view.CommentView;
import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.repository.TopicRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepo topicRepo;
    private final HabitatService habitatService;
    private final PictureService pictureService;
    private final UserService userService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicService(TopicRepo topicRepo, HabitatService habitatService,
                        PictureService pictureService, UserService userService,
                        CommentService commentService, ModelMapper modelMapper) {
        this.topicRepo = topicRepo;
        this.habitatService = habitatService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    public Topic getMostCommented() {
        //TODO check for null value
        return topicRepo.getMostCommented().orElse(null);
    }

    public void AddTopic(TopicAddDTO topicAddDTO) throws IOException {
        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setApproved(false);
        topic.setHabitat(habitatService.findHabitatByName(topicAddDTO.getHabitat()));
        topic.setLevel(LevelEnum.valueOf(topicAddDTO.getLevel()));
        topic.setPictures(List.of(pictureService.addPictureToNewTopic(topicAddDTO.getPictureFile())));
        topic.setAuthor(userService.findUserByUsername(topicAddDTO.getUserName()));
        topic.setComments(new ArrayList<>());

        topicRepo.save(topic);
    }

    public List<Topic> getAllApprovedTopics() {
        //TODO check for null value
        return topicRepo.findAllByApproved(true).orElse(null);
    }

    public List<Topic> getAllNotApprovedTopics() {
        //TODO check for null value
        return topicRepo.findAllByApproved(false).orElse(null);
    }

    public Topic findLatestTopic() {
        //TODO check for null value
        return topicRepo.findLatestTopic().orElse(null);
    }

    public Topic findById(Long id) {
        //TODO check for null value
        return topicRepo.findById(id).orElse(null);
    }

    public void approve(Long id) {
        //TODO check for null value
        Topic topic = topicRepo.findById(id).orElse(null);

        topic.setApproved(true);
        topicRepo.save(topic);
    }

    public void remove(Long id) {
        //TODO check for null or wrong value
        Topic topic = topicRepo.findById(id).orElse(null);

        for (Comment comment : topic.getComments()) {
            commentService.remove(comment);
        }

        for (Picture picture : topic.getPictures()) {
            pictureService.remove(picture);
        }

        topicRepo.delete(topic);
    }

    public TopicsDetailsView mapTopicsDetailsView(Topic topic) {
        //TODO check for null value
        TopicsDetailsView topicsDetailsView = modelMapper.map(topic, TopicsDetailsView.class);

        List<PictureView> pictureViews = pictureService.mapPictureViewList(topic.getPictures());
        topicsDetailsView.setPictures(pictureViews);

        List<CommentView> commentViews = commentService.mapCommentViewList(topic.getComments());
        topicsDetailsView.setComments(commentViews);

        topicsDetailsView.setHabitat(topic.getHabitat().getName().toString());

        topicsDetailsView.setLevel(topic.getLevel().toString());

        topicsDetailsView.setAuthor(topic.getAuthor().getUsername());
        return topicsDetailsView;
    }

    public List<TopicsView> mapTopicDetailsViewList(List<Topic> topics) {
        //TODO check for null value
        return topics.stream()
                .map(e -> modelMapper.map(e, TopicsView.class))
                .collect(Collectors.toList());
    }
}
