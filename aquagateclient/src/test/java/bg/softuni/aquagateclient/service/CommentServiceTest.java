package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.rest.CommentRestService;
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

class CommentServiceTest {
    private final CommentRestService commentRestService;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentAddDTO commentAddDTO;
    private final UserEntity userEntity;
    private final CommentView commentView;

    CommentServiceTest() {
        commentRestService = mock(CommentRestService.class);
        userService = mock(UserService.class);
        commentService = new CommentService(commentRestService, userService);

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setTopicId(1L);
        commentAddDTO.setAuthor("testUsername");
        commentAddDTO.setContext("To test");

        userEntity = new UserEntity();
        userEntity.setId(5L);

        commentView = new CommentView();
        commentView.setAuthor("testUsername");
        commentView.setContext("To test");
        commentView.setId(7L);
    }

    @Test
    void testAddCommentSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange

        when(userService.findUserByUsername("testUsername"))
                .thenReturn(userEntity);

        Long id = userEntity.getId();

        when(commentRestService.doAddComment(commentAddDTO, id)).thenReturn(ResponseEntity.ok(commentView));

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
        when(userService.findUserByUsername("testUsername"))
                .thenReturn(userEntity);
        Long id = userEntity.getId();

        when(commentRestService.doAddComment(commentAddDTO, id)).thenThrow(RestClientException.class);

        // Act //Assert
        assertThrows(BadRequestException.class, () -> commentService.addComment(commentAddDTO));
    }
}