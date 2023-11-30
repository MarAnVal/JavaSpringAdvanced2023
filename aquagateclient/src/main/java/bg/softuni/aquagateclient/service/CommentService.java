package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationCommentConfiguration;
import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {
    private final ApplicationCommentConfiguration applicationCommentConfiguration;
    private final RestTemplate restTemplate;

    public CommentService(ApplicationCommentConfiguration applicationCommentConfiguration, RestTemplate restTemplate) {
        this.applicationCommentConfiguration = applicationCommentConfiguration;
        this.restTemplate = restTemplate;
    }

    public void addComment(CommentAddDTO commentAddDTO) {
        String url = applicationCommentConfiguration.commentAddUrlSource();
        HttpEntity<CommentAddDTO> http = new HttpEntity<>(commentAddDTO);
        ResponseEntity<CommentView> exchange = restTemplate.exchange(url, HttpMethod.POST, http, CommentView.class);

        if (exchange.getStatusCode().value() != 200 || exchange.getBody() == null) {
            //TODO throw proper errors for the returned status codes
            throw new RuntimeException(String.valueOf(exchange.getStatusCode()));
        }
    }
}
