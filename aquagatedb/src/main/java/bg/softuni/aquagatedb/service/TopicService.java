package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.model.dto.view.TopicDetailsView;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.web.error.HabitatNotFoundException;
import bg.softuni.aquagatedb.web.error.PictureNotFoundException;
import bg.softuni.aquagatedb.web.error.TopicNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepo topicRepo;
    private final HabitatService habitatService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicService(TopicRepo topicRepo, HabitatService habitatService,
                        PictureService pictureService,ModelMapper modelMapper) {
        this.topicRepo = topicRepo;
        this.habitatService = habitatService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    public TopicView addTopic(TopicAddDTO topicAddDTO) throws HabitatNotFoundException, TopicNotFoundException,
            PictureNotFoundException {

        if (topicAddDTO.getVideoUrl() == null) {
            topicAddDTO.setVideoUrl("MebHynnz0FY");
        }

        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setApproved(false);
        topic.setHabitat(habitatService.findHabitatByName(topicAddDTO.getHabitat()));

        Picture picture = pictureService.addPicture(topicAddDTO.getPictureUrl());
        topic.setPicture(picture);

        topic.setComments(new ArrayList<>());

        topicRepo.save(topic);

        return mapTopicView(topicRepo
                .findTopicByDescriptionAndName(topicAddDTO.getDescription(), topicAddDTO.getName())
                .get(0));
    }

    public TopicDetailsView approveTopic(Long id) throws TopicNotFoundException, PictureNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        topic.setApproved(true);
        topicRepo.save(topic);

        return mapTopicDetailView(topicRepo.findById(id).orElseThrow(TopicNotFoundException::new));
    }

    public void removeTopic(Long id) throws TopicNotFoundException {
        Topic topic = topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);

        topicRepo.delete(topic);
    }

    public List<TopicView> findAllTopics() {
        return topicRepo.findAll().stream()
                .map(this::mapTopicView)
                .collect(Collectors.toList());
    }

    private TopicView mapTopicView(Topic topic) {
        TopicView topicView = modelMapper.map(topic, TopicView.class);

        topicView.setPictureUrl(topic.getPicture().getPictureUrl());
        topicView.setCommentCount(topic.getComments().size());

        return topicView;
    }

    public TopicDetailsView findTopicById(Long id) throws TopicNotFoundException {
        return mapTopicDetailView(topicRepo.findById(id).orElseThrow(TopicNotFoundException::new));
    }

    private TopicDetailsView mapTopicDetailView(Topic topic) {
        TopicDetailsView topicDetailsView = modelMapper.map(topic, TopicDetailsView.class);

        topicDetailsView.setHabitat(topic.getHabitat().getName().toString());
        topicDetailsView.setComments(topic.getComments()
                .stream()
                .map(e -> modelMapper.map(e, CommentView.class))
                .collect(Collectors.toList())
        );
        topicDetailsView.setPicture(topic.getPicture().getPictureUrl());

        return topicDetailsView;
    }

    public void initTestData() throws TopicNotFoundException, HabitatNotFoundException, PictureNotFoundException{
        if (topicRepo.count() < 1) {
            TopicAddDTO topicAddDTO = new TopicAddDTO();
            topicAddDTO.setName("Test");
            topicAddDTO.setDescription("To do test");
            topicAddDTO.setLevel("BEGINNER");
            topicAddDTO.setHabitat("BLACK_WATER");
            topicAddDTO.setVideoUrl("MebHynnz0FY?si");
            topicAddDTO.setPictureUrl("/images/picture-not-found-icon.png");
            topicAddDTO.setAuthor(1L);
            this.addTopic(topicAddDTO);
        }
    }

    public Topic findTopicToAddComment(Long id) throws TopicNotFoundException {
        return topicRepo.findById(id).orElseThrow(TopicNotFoundException::new);
    }
}
