package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.service.rest.CommentRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {
    private final CommentRestService commentRestService;
    private final RestTemplate restTemplate;
    private final UserService userService;

    public CommentService(CommentRestService commentRestService,
                          RestTemplate restTemplate, UserService userService) {
        this.commentRestService = commentRestService;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public ResponseEntity<CommentView> addComment(CommentAddDTO commentAddDTO) throws BadRequestException,
            ObjectNotFoundException {
        try {
            String url = commentRestService.commentAddUrl();

            Long id = userService.findUserByUsername(commentAddDTO.getAuthor()).getId();

            HttpEntity<CommentRequestAddDTO> http = commentRestService
                    .getHttpAddComment(commentAddDTO, id);

            return restTemplate.exchange(url, HttpMethod.POST, http, CommentView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }
}
