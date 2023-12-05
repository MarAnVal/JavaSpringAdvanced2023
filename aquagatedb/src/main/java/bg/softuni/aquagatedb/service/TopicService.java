package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.model.dto.view.TopicDetailsView;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TopicService {

    private final TopicRepo topicRepo;
    private final HabitatService habitatService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicService(TopicRepo topicRepo, HabitatService habitatService,
                        PictureService pictureService, ModelMapper modelMapper) {
        this.topicRepo = topicRepo;
        this.habitatService = habitatService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    public TopicView addTopic(TopicAddDTO topicAddDTO) throws ObjectNotFoundException {

        if (topicAddDTO.getVideoUrl() == null) {
            topicAddDTO.setVideoUrl("MebHynnz0FY");
        }

        Topic topic = modelMapper.map(topicAddDTO, Topic.class);
        topic.setDate(LocalDate.now(ZoneOffset.UTC));
        topic.setApproved(false);

        topic.setHabitat(habitatService.findHabitatByName(topicAddDTO.getHabitat()));

        topic.setPicture(pictureService.addPicture(topicAddDTO.getPictureUrl()));

        topic.setComments(new ArrayList<>());

        topicRepo.save(topic);

        Topic savedTopic = topicRepo.findAll().stream()
                .max((e1, e2) -> {
                    Long id1 = e1.getId();
                    Long id2 = e2.getId();
                    return id1.compareTo(id2);
                }).orElse(null);

        if (savedTopic == null ||
                savedTopic.getDate().isBefore(LocalDate.now(ZoneOffset.UTC)) ||
                !Objects.equals(savedTopic.getDescription(), topicAddDTO.getDescription()) ||
                !Objects.equals(savedTopic.getName(), topicAddDTO.getName()) ||
                !Objects.equals(savedTopic.getAuthor(), topicAddDTO.getAuthor())) {

            throw new ObjectNotFoundException("Problem with saving the topic! Please try again!");
        }

        return mapTopicView(savedTopic);
    }

    public TopicDetailsView approveTopic(Long id) throws ObjectNotFoundException {

        if (topicRepo.findById(id).isEmpty()) {
            throw new ObjectNotFoundException("Topic not found!");
        } else {
            Topic topic = topicRepo.findById(id).get();
            topic.setApproved(true);
            topicRepo.save(topic);

            return mapTopicDetailView(topicRepo.findById(id).get());
        }
    }

    public boolean removeTopic(Long id) throws ObjectNotFoundException {
        if (topicRepo.findById(id).isEmpty()) {
            throw new ObjectNotFoundException("Topic not found!");
        } else {
            Topic topic = topicRepo.findById(id).get();
            topicRepo.delete(topic);

            return true;
        }
    }

    public List<TopicView> findAllTopics() {

        List<Topic> all = topicRepo.findAll();

        if (all.isEmpty()) {
            return new ArrayList<>();

        } else {

            return all.stream()
                    .map(this::mapTopicView)
                    .toList();
        }
    }

    private TopicView mapTopicView(Topic topic) {

        TopicView topicView = modelMapper.map(topic, TopicView.class);

        topicView.setPictureUrl(topic.getPicture().getPictureUrl());
        topicView.setCommentCount(topic.getComments().size());

        return topicView;
    }

    public TopicDetailsView findTopicById(Long id) throws ObjectNotFoundException {

        if (topicRepo.findById(id).isEmpty()) {

            throw new ObjectNotFoundException("Topic not found!");
        } else {

            Topic topic = topicRepo.findById(id).get();
            return mapTopicDetailView(topic);
        }
    }

    private TopicDetailsView mapTopicDetailView(Topic topic) {

        TopicDetailsView topicDetailsView = modelMapper.map(topic, TopicDetailsView.class);

        topicDetailsView.setHabitat(topic.getHabitat().getName().toString());
        topicDetailsView.setComments(topic.getComments()
                .stream()
                .map(e -> modelMapper.map(e, CommentView.class))
                .toList()
        );
        topicDetailsView.setPicture(topic.getPicture().getPictureUrl());

        return topicDetailsView;
    }

    public Topic findTopicByIdToAddComment(Long id) throws ObjectNotFoundException {

        if (topicRepo.findById(id).isEmpty()) {

            throw new ObjectNotFoundException("Topic not found!");
        } else {

            return topicRepo.findById(id).get();
        }
    }

    public void removeAllNotApprovedTopicsBeforeDate(LocalDate localDate) {

        if (!topicRepo.findAll().isEmpty()) {
            List<Topic> list = topicRepo.findAll().stream()
                    .filter(e -> e.getDate().isBefore(localDate) && !e.getApproved())
                    .toList();

            if (!list.isEmpty()) {
                topicRepo.deleteAll(list);
            }
        }
    }
}
