package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.model.entity.Comment;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    private final TopicService topicService;

    @Autowired
    public CommentService(CommentRepo commentRepo, TopicService topicService) {
        this.commentRepo = commentRepo;
        this.topicService = topicService;
    }

    public CommentView addComment(CommentAddDTO commentAddDTO) throws ObjectNotFoundException {
        Comment comment = new Comment();
        comment.setContext(commentAddDTO.getContext());
        comment.setAuthorId(commentAddDTO.getAuthorId());

        Topic topic = topicService.findTopicToAddComment(commentAddDTO.getTopicId());
        comment.setTopic(topic);
        commentRepo.save(comment);

        Comment addedComment = commentRepo.findAllByContextAndTopicIdAndAuthorIdOrderByIdDesc(commentAddDTO.getContext(),
                commentAddDTO.getTopicId(), commentAddDTO.getAuthorId()).get(0);

        return mapCommentView(addedComment);
    }

    private CommentView mapCommentView(Comment comment) {
        CommentView commentView = new CommentView();
        commentView.setId(comment.getId());
        commentView.setContext(comment.getContext());
        commentView.setAuthorId(comment.getAuthorId());
        commentView.setTopicId(comment.getTopic().getId());
        return commentView;
    }

    public void initTestData() throws ObjectNotFoundException {
        if (commentRepo.count() < 1) {
            CommentAddDTO commentAddDTO = new CommentAddDTO();
            commentAddDTO.setTopicId(1L);
            commentAddDTO.setContext("to do first test");
            commentAddDTO.setAuthorId(1L);
            this.addComment(commentAddDTO);

            commentAddDTO.setContext("to do second test");
            commentAddDTO.setAuthorId(2L);
            this.addComment(commentAddDTO);
        }
    }

    public void removeCommentsByTopicId(Long id) {
        List<Comment> comments = commentRepo.findAllByTopicId(id);
        commentRepo.deleteAll(comments);
    }
}
