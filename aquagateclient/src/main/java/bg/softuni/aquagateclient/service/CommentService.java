package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationCommentConfiguration;
import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.web.error.CommentNotFoundException;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {
    private final ApplicationCommentConfiguration applicationCommentConfiguration;
    private final RestTemplate restTemplate;
    private final UserService userService;

    public CommentService(ApplicationCommentConfiguration applicationCommentConfiguration,
                          RestTemplate restTemplate, UserService userService) {
        this.applicationCommentConfiguration = applicationCommentConfiguration;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public void addComment(CommentAddDTO commentAddDTO) throws UserNotFoundException, CommentNotFoundException {
        String url = applicationCommentConfiguration.commentAddUrlSource();
        CommentRequestAddDTO commentRequestAddDTO = mapCommentRequestAddDTO(commentAddDTO);

        HttpEntity<CommentRequestAddDTO> http = new HttpEntity<>(commentRequestAddDTO);
        ResponseEntity<CommentRequestAddDTO> exchange = restTemplate
                .exchange(url, HttpMethod.POST, http, CommentRequestAddDTO.class);

        if (exchange.getStatusCode().value() != 200 || exchange.getBody() == null) {
            //TODO throw proper errors for the returned status codes
            throw new CommentNotFoundException();
        }
    }

    private CommentRequestAddDTO mapCommentRequestAddDTO(CommentAddDTO commentAddDTO) throws UserNotFoundException {
        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setContext(commentAddDTO.getContext());
        commentRequestAddDTO.setTopicId(commentAddDTO.getTopicId());
        commentRequestAddDTO.setAuthorId(userService.getUserByUsername(commentAddDTO.getAuthor()).getId());

        return commentRequestAddDTO;
    }
}
