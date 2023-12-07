package bg.softuni.aquagateclient.service.rest;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.CommentService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.service.rest.util.CommentRestUtil;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommentRestServiceTest {
    private final CommentRestService commentRestService;
    private final RestTemplate restTemplate;
    private final CommentRestUtil commentRestUtil;
    private final CommentAddDTO commentAddDTO;
    private final CommentView commentView;
    private final HttpEntity<CommentRequestAddDTO> http;

    CommentRestServiceTest() {
        restTemplate = mock(RestTemplate.class);
        commentRestUtil = mock(CommentRestUtil.class);
        commentRestService = new CommentRestService(restTemplate, commentRestUtil);

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setTopicId(1L);
        commentAddDTO.setAuthor("testUsername");
        commentAddDTO.setContext("To test");

        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setTopicId(1L);
        commentRequestAddDTO.setContext("To test");
        commentRequestAddDTO.setAuthorId(5L);

        http = new HttpEntity<>(commentRequestAddDTO);

        commentView = new CommentView();
        commentView.setAuthor("testUsername");
        commentView.setContext("To test");
        commentView.setId(7L);
    }

    @Test
    void testAddCommentSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        when(commentRestUtil.commentAddUrl()).thenReturn("testUrl");

        Long id = 5L;

        when(commentRestUtil.getHttpAddComment(commentAddDTO, id))
                .thenReturn(http);

        when(restTemplate.exchange("testUrl",
                HttpMethod.POST, http,
                CommentView.class))
                .thenReturn(ResponseEntity.ok(commentView));

        // Act
        ResponseEntity<CommentView> commentViewResponseEntity = commentRestService.doAddComment(commentAddDTO, id);
        CommentView body = commentViewResponseEntity.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(commentViewResponseEntity.getStatusCode().value(), 200);
        assertEquals(commentViewResponseEntity.getBody(), commentView);
    }

    @Test
    void testAddCommentUnsuccessful404() {
        // Arrange
        when(commentRestUtil.commentAddUrl()).thenReturn("testUrl");

        Long id = 5L;

        when(commentRestUtil.getHttpAddComment(commentAddDTO, id))
                .thenReturn(http);

        when(restTemplate.exchange("testUrl",
                HttpMethod.POST, http,
                CommentView.class))
                .thenReturn(ResponseEntity.notFound().build());

        // Act //Assert
        assertThrows(RestClientException.class, () -> commentRestService.doAddComment(commentAddDTO, id));
    }
}