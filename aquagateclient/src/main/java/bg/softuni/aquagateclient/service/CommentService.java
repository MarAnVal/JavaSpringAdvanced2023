package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationCommentConfiguration;
import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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

    public void addComment(CommentAddDTO commentAddDTO) throws BadRequestException, ObjectNotFoundException {
        try {
            String url = applicationCommentConfiguration.commentAddUrlSource();
            CommentRequestAddDTO commentRequestAddDTO = mapCommentRequestAddDTO(commentAddDTO);

            HttpEntity<CommentRequestAddDTO> http = new HttpEntity<>(commentRequestAddDTO);
            ResponseEntity<CommentRequestAddDTO> exchange = restTemplate
                    .exchange(url, HttpMethod.POST, http, CommentRequestAddDTO.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    private CommentRequestAddDTO mapCommentRequestAddDTO(CommentAddDTO commentAddDTO) throws ObjectNotFoundException {
        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setContext(commentAddDTO.getContext());
        commentRequestAddDTO.setTopicId(commentAddDTO.getTopicId());
        commentRequestAddDTO.setAuthorId(userService.findUserByUsername(commentAddDTO.getAuthor()).getId());

        return commentRequestAddDTO;
    }
}
