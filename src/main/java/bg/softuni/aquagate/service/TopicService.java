package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Comment;
import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.data.model.CommentAddDTO;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.view.CommentView;
import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.repository.TopicRepo;
import bg.softuni.aquagate.web.error.*;
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

    public Topic getMostCommented() throws TopicNotFoundException {
        return topicRepo.getMostCommented().orElseThrow(TopicNotFoundException::new);
    }

    public Topic getNoTopicUnit() {
        Topic topic = new Topic();
        topic.setName("No topics at the moment");
        topic.setPictures(pictureService.getNoPicturesUnit());
        return topic;
    }

    public void addTopic(TopicAddDTO topicAddDTO) throws IOException, UserNotFoundException, HabitatNotFoundException,
            PictureNotFoundException {
        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setApproved(false);
        topic.setHabitat(habitatService.findHabitatByName(topicAddDTO.getHabitat()));
        topic.setLevel(LevelEnum.valueOf(topicAddDTO.getLevel()));
        if (topicAddDTO.getPictureFile().isEmpty()) {
            topic.setPictures(new ArrayList<>());
        } else {
            Picture picture = pictureService.addPictureToNewTopic(topicAddDTO.getPictureFile());
            topic.setPictures(List.of(picture));
        }
        topic.setAuthor(userService.findUserByUsername(topicAddDTO.getUserName()));
        topic.setComments(new ArrayList<>());

        topicRepo.save(topic);
    }

    public List<Topic> getAllApprovedTopics() throws TopicNotFoundException {
        return topicRepo.findAllByApproved(true).orElseThrow(TopicNotFoundException::new);
    }
    public List<Topic> getAllApprovedMyTopics(String username) throws TopicNotFoundException, UserNotFoundException {
        UserEntity user = userService.findUserByUsername(username);
        return topicRepo.findAllApprovedByUserId(user.getId()).orElseThrow(TopicNotFoundException::new);
    }

    public List<Topic> getAllNotApprovedTopics() throws TopicNotFoundException {
        return topicRepo.findAllByApproved(false).orElseThrow(TopicNotFoundException::new);
    }

    public Topic findLatestTopic() throws TopicNotFoundException {
        return topicRepo.findLatestTopic().orElseThrow(TopicNotFoundException::new);
    }

    public Topic findById(Long id) throws TopicNotFoundException {
        return topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);
    }

    public void approve(Long id) throws TopicNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        topic.setApproved(true);
        topicRepo.save(topic);
    }

    public void remove(Long id) throws CommentNotFoundException, TopicNotFoundException, PictureNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        for (Comment comment : topic.getComments()) {
            commentService.remove(comment);
        }

        for (Picture picture : topic.getPictures()) {
            pictureService.remove(picture);
        }

        topicRepo.delete(topic);
    }

    public void addComment(CommentAddDTO commentAddDTO) throws UserNotFoundException, TopicNotFoundException {
        Comment comment = modelMapper.map(commentAddDTO, Comment.class);
        UserEntity user = userService.findUserByUsername(commentAddDTO.getAuthorUsername());
        Topic topic = topicRepo.findById(commentAddDTO.getTopicId()).orElseThrow(TopicNotFoundException::new);

        comment.setAuthor(user);
        topic.getComments().add(comment);

        topicRepo.save(topic);
    }

    public void addPicture(PictureAddDTO pictureAddDTO) throws TopicNotFoundException {
        Picture picture = modelMapper.map(pictureAddDTO, Picture.class);
        Topic topic = topicRepo.findById(pictureAddDTO.getTopicId()).orElseThrow(TopicNotFoundException::new);

        topic.getPictures().add(picture);

        topicRepo.save(topic);
    }


    public TopicsDetailsView mapTopicsDetailsView(Topic topic) {
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
        return topics.stream()
                .map(e -> modelMapper.map(e, TopicsView.class))
                .collect(Collectors.toList());
    }
}
