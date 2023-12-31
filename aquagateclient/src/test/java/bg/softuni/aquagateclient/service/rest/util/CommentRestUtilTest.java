package bg.softuni.aquagateclient.service.rest.util;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.service.rest.util.CommentRestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommentRestUtilTest {
    private final CommentAddDTO commentAddDTO;
    private final CommentRestUtil commentRestUtil;

    CommentRestUtilTest() {
        commentRestUtil = new CommentRestUtil("testUrl");

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setContext("to test");
        commentAddDTO.setAuthor("testUsername");
        commentAddDTO.setTopicId(1L);
    }
    @Test
    void testGetHttpAddComment() {
        // Arrange // Act
        HttpEntity<CommentRequestAddDTO> http = commentRestUtil.getHttpAddComment(commentAddDTO, 7L);
        CommentRequestAddDTO body = http.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(commentAddDTO.getContext(), body.getContext());
        assertEquals(commentAddDTO.getTopicId(), body.getTopicId());
        assertEquals(7L, body.getAuthorId());
    }

    @Test
    void testCommentAddUrl() {
        // Arrange // Act // Assert
        assertEquals(commentRestUtil.commentAddUrl(), "testUrl");
    }
}