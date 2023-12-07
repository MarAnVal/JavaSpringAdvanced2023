package bg.softuni.aquagateclient.service.rest;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.service.rest.util.CommentRestUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentRestService {

    private final RestTemplate restTemplate;
    private final CommentRestUtil commentRestUtil;

    public CommentRestService(RestTemplate restTemplate, CommentRestUtil commentRestUtil) {
        this.restTemplate = restTemplate;
        this.commentRestUtil = commentRestUtil;
    }

    public ResponseEntity<CommentView> doAddComment(CommentAddDTO commentAddDTO, Long id) {

        String url = commentRestUtil.commentAddUrl();

        HttpEntity<CommentRequestAddDTO> http = commentRestUtil
                .getHttpAddComment(commentAddDTO, id);

        ResponseEntity<CommentView> exchange = restTemplate.exchange(url, HttpMethod.POST, http, CommentView.class);
        if (exchange.getStatusCode().value() != 200) {
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

}
