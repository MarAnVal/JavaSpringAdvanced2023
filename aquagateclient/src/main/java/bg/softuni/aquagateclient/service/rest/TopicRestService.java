package bg.softuni.aquagateclient.service.rest;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.rest.util.TopicRestUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TopicRestService {

    private final TopicRestUtil topicRestUtil;
    private final RestTemplate restTemplate;

    public TopicRestService(TopicRestUtil topicRestUtil, RestTemplate restTemplate) {
        this.topicRestUtil = topicRestUtil;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<TopicView>> getAllTopics() {
            String url = topicRestUtil.topicsAllUrlSource();

        ResponseEntity<List<TopicView>> exchange = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestUtil.getParameterizedTypeReferenceTopicViewList());

        if(exchange.getStatusCode().value() != 200){
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

    public ResponseEntity<TopicView> doRemoveTopic(Long id) {
        String url = topicRestUtil.topicRemoveUrlSource() + "/" + id;

        ResponseEntity<TopicView> exchange = restTemplate
                .exchange(url, HttpMethod.DELETE, null, TopicView.class);
        if(exchange.getStatusCode().value() != 200){
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

    public ResponseEntity<TopicView> doApproveTopic(Long id) {
        String url = topicRestUtil.topicApproveUrlSource() + "/" + id;
        ResponseEntity<TopicView> exchange = restTemplate
                .exchange(url, HttpMethod.POST, null, TopicView.class);
        if(exchange.getStatusCode().value() != 200){
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

    public ResponseEntity<TopicView> doAddTopic(TopicAddDTO topicAddDTO, String pictureUrl) {
        String url = topicRestUtil.topicAddUrlSource();
        HttpEntity<TopicRequestAddDTO> http = topicRestUtil.getHttpAddTopic(topicAddDTO, pictureUrl);


        ResponseEntity<TopicView> exchange = restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class);
        if(exchange.getStatusCode().value() != 200){
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

    public ResponseEntity<TopicDetailsRequestDTO> getTopicDetails(Long id){
        String url = topicRestUtil.topicDetailsUrlSource() + "/" + id;

        ResponseEntity<TopicDetailsRequestDTO> exchange = restTemplate
                .exchange(url, HttpMethod.GET, null, TopicDetailsRequestDTO.class);
        if(exchange.getStatusCode().value() != 200){
            throw new RestClientException("Status code " + exchange.getStatusCode().value());
        }
        return exchange;
    }

}
