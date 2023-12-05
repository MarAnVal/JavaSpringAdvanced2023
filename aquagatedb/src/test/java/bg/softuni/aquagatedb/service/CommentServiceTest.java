package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.model.entity.Comment;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommentServiceTest {
    private final CommentRepo commentRepo;
    private final TopicService topicService;
    private final CommentAddDTO commentAddDTO;
    private final CommentService commentService;

    CommentServiceTest() {
        commentRepo = mock(CommentRepo.class);
        topicService = mock(TopicService.class);
        commentService = new CommentService(commentRepo, topicService);

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setAuthorId(1L);
        commentAddDTO.setContext("to do test");
        commentAddDTO.setTopicId(10L);

    }

    @Test
    void testAddComment() throws ObjectNotFoundException {
        // Arrange
        Topic topic = new Topic();
        topic.setId(10L);

        when(topicService.findTopicByIdToAddComment(commentAddDTO.getTopicId())). thenReturn(topic);

        Comment comment = new Comment();
        comment.setAuthorId(commentAddDTO.getAuthorId());
        comment.setTopic(topic);
        comment.setContext(commentAddDTO.getContext());
        comment.setId(5L);
        comment.setAuthorId(comment.getAuthorId());

        when(commentRepo.findAllByContextAndTopicIdAndAuthorIdOrderByIdDesc(commentAddDTO.getContext(),
                commentAddDTO.getTopicId(), commentAddDTO.getAuthorId()))
                .thenReturn(List.of(comment));

        // Act
        CommentView commentView = commentService.addComment(commentAddDTO);

        // Assert
        assertEquals(comment.getId(), commentView.getId());
        assertEquals(commentAddDTO.getTopicId(), commentView.getTopicId());
        assertEquals(commentAddDTO.getAuthorId(), commentView.getAuthorId());
        assertEquals(commentAddDTO.getContext(), commentView.getContext());

    }

    @Test
    void testRemoveCommentsByTopicId() {
        // Arrange
        when(commentRepo.findAllByTopicId(1L)).thenReturn(new ArrayList<>());

        // Act
        boolean b = commentService.removeCommentsByTopicId(1L);

        // Assert
        assertTrue(b);
    }
}