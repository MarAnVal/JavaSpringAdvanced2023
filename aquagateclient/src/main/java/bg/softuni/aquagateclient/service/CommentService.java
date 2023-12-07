package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.service.rest.CommentRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class CommentService {
    private final CommentRestService commentRestService;
    private final UserService userService;

    public CommentService(CommentRestService commentRestService, UserService userService) {
        this.commentRestService = commentRestService;
        this.userService = userService;
    }

    public ResponseEntity<CommentView> addComment(CommentAddDTO commentAddDTO) throws BadRequestException,
            ObjectNotFoundException {
        try {

            Long id = userService.findUserByUsername(commentAddDTO.getAuthor()).getId();
            return commentRestService.doAddComment(commentAddDTO, id);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }


}
