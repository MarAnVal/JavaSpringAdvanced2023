package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.data.entity.Topic;
import bg.softuni.aquagatedb.data.model.TopicAddDTO;
import bg.softuni.aquagatedb.data.view.TopicDetailsView;
import bg.softuni.aquagatedb.data.view.TopicView;
import bg.softuni.aquagatedb.service.TopicService;
import bg.softuni.aquagatedb.web.controller.TopicsController;
import bg.softuni.aquagatedb.web.error.HabitatNotFoundException;
import bg.softuni.aquagatedb.web.error.TopicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicsControllerImpl implements TopicsController {

    private final TopicService topicService;

    @Autowired
    public TopicsControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public ResponseEntity<List<TopicView>> getAllTopics() {
        //TODO
        List<Topic> allTopics = topicService.findAllTopics();
        return null;
    }

    @Override
    public ResponseEntity<TopicDetailsView> getTopicDetails(Long id) {
        //TODO add the comments too
        try {
            topicService.findTopicById(id)
        } catch (TopicNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<TopicView> doRemove(Long id) {
        //TODO exception handling
        try {
            topicService.remove(id);
        } catch (TopicNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<TopicView> doApprove(Long id) {
        //TODO exception handling
        try {
            topicService.approve(id);
        } catch (TopicNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<TopicView> doTopicAdd(TopicAddDTO topicAddDTO, BindingResult bindingResult) {
        //TODO validate topicAddDTO
        try {
            topicService.addTopic(topicAddDTO);
        } catch (HabitatNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
