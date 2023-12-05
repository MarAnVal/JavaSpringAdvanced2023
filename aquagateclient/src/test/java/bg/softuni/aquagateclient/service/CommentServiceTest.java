package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.rest.CommentRestService;
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

class CommentServiceTest {
    private final CommentRestService commentRestService;
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentAddDTO commentAddDTO;
    private final UserEntity userEntity;
    private final HttpEntity<CommentRequestAddDTO> http;
    private final CommentView commentView;

    CommentServiceTest() {
        commentRestService = mock(CommentRestService.class);

        restTemplate = mock(RestTemplate.class);

        userService = mock(UserService.class);

        commentService = new CommentService(commentRestService, restTemplate, userService);

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setTopicId(1L);
        commentAddDTO.setAuthor("testUsername");
        commentAddDTO.setContext("To test");

        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setTopicId(1L);
        commentRequestAddDTO.setContext("To test");
        commentRequestAddDTO.setAuthorId(5L);

        userEntity = new UserEntity();
        userEntity.setId(5L);

        http = new HttpEntity<>(commentRequestAddDTO);

        commentView = new CommentView();
        commentView.setAuthor("testUsername");
        commentView.setContext("To test");
        commentView.setId(7L);
    }

    @Test
    void testAddCommentSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        when(commentRestService.commentAddUrl()).thenReturn("testUrl");

        when(userService.findUserByUsername("testUsername"))
                .thenReturn(userEntity);

        when(commentRestService.getHttpAddComment(commentAddDTO, 5L))
                .thenReturn(http);

        when(restTemplate.exchange("testUrl",
                HttpMethod.POST, http,
                CommentView.class))
                .thenReturn(ResponseEntity.ok(commentView));

        // Act
        ResponseEntity<CommentView> commentViewResponseEntity = commentService.addComment(commentAddDTO);
        CommentView body = commentViewResponseEntity.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(commentViewResponseEntity.getStatusCode().value(), 200);
        assertEquals(commentViewResponseEntity.getBody(), commentView);
    }

    @Test
    void testAddCommentUnsuccessful() throws ObjectNotFoundException {
        // Arrange
        when(commentRestService.commentAddUrl()).thenReturn("testUrl");

        when(userService.findUserByUsername("testUsername"))
                .thenReturn(userEntity);

        when(commentRestService.getHttpAddComment(commentAddDTO, 5L))
                .thenReturn(http);

        when(restTemplate.exchange("testUrl",
                HttpMethod.POST, http,
                CommentView.class))
                .thenThrow(RestClientException.class);

        // Act //Assert
        assertThrows(BadRequestException.class, () -> commentService.addComment(commentAddDTO));
    }
}