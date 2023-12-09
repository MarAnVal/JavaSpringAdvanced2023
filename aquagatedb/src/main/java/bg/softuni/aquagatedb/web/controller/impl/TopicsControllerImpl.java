package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.TopicDetailsView;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.service.CommentService;
import bg.softuni.aquagatedb.service.TopicService;
import bg.softuni.aquagatedb.web.controller.TopicsController;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class TopicsControllerImpl implements TopicsController {

    private final TopicService topicService;
    private final CommentService commentService;

    @Autowired
    public TopicsControllerImpl(TopicService topicService, CommentService commentService) {
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<List<TopicView>> getAllTopics() {

        List<TopicView> allTopics = topicService.findAllTopics();
        return ResponseEntity.ok(allTopics);
    }

    @Override
    public ResponseEntity<TopicDetailsView> getTopicDetails(Long id) throws ObjectNotFoundException {

        return ResponseEntity.ok(topicService.findTopicById(id));
    }

    @Override
    public ResponseEntity<TopicView> doRemove(Long id) throws ObjectNotFoundException {

        commentService.removeCommentsByTopicId(id);
        topicService.removeTopic(id);
        return ResponseEntity.ok(new TopicView());
    }

    @Override
    public ResponseEntity<TopicDetailsView> doApprove(Long id) throws ObjectNotFoundException {

        return ResponseEntity.ok(topicService.approveTopic(id));
    }

    @Override
    public ResponseEntity<TopicView> doTopicAdd(@RequestBody TopicAddDTO topicAddDTO, BindingResult bindingResult) {
        Pattern pattern = Pattern.compile(".jpeg$|.jpg$|.bnp$|.png$");
        Matcher matcher = pattern.matcher(topicAddDTO.getPictureUrl());

        if (bindingResult.hasErrors() || !matcher.find()) {

            return ResponseEntity.unprocessableEntity().build();
        }
        try {

            return ResponseEntity.ok(topicService.addTopic(topicAddDTO));

        } catch (ObjectNotFoundException e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getApprovedTopicsCount() {
        return ResponseEntity.ok(topicService.findAllTopics().stream()
                .filter(TopicView::getApproved).toList().size());
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<TopicView> handleApplicationExceptions() {

        return ResponseEntity.notFound().build();
    }
}
