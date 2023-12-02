package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationTopicsConfiguration;
import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.view.*;
import bg.softuni.aquagateclient.web.error.TopicNotFoundException;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final ApplicationTopicsConfiguration applicationTopicsConfiguration;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;
    private final UserService userService;

    public TopicService(ApplicationTopicsConfiguration applicationTopicsConfiguration,
                        RestTemplate restTemplate, ModelMapper modelMapper,
                        CloudService cloudService, UserService userService) {
        this.applicationTopicsConfiguration = applicationTopicsConfiguration;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
        this.userService = userService;
    }


    private ResponseEntity<List<TopicView>> getAllTopics() {
        String url = applicationTopicsConfiguration.topicsAllUrlSource();
        return restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TopicView>>() {
                });
    }

    public void removeTopic(Long id) throws TopicNotFoundException {
        String url = applicationTopicsConfiguration.topicRemoveUrlSource() + "/" + id;
        ResponseEntity<TopicView> exchange = restTemplate
                .exchange(url, HttpMethod.DELETE, null, TopicView.class);
        //TODO check the returned status value
        if (exchange.getStatusCode().value() != 200) {
            throw new TopicNotFoundException();
        }
    }

    public List<TopicView> getAllNotApprovedTopics() throws TopicNotFoundException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();

        if (allTopics.getStatusCode().value() != 200 || allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            //TODO throw proper errors
            throw new TopicNotFoundException();
        } else {
            return allTopics.getBody()
                    .stream()
                    .filter(e -> !e.getApproved()).collect(Collectors.toList());
        }
    }

    public void approveTopic(Long id) throws TopicNotFoundException {
        String url = applicationTopicsConfiguration.topicApproveUrlSource() + "/" + id;
        ResponseEntity<TopicView> exchange = restTemplate
                .exchange(url, HttpMethod.POST, null, TopicView.class);

        //TODO check the returned status value!
        if (exchange.getStatusCode().value() != 200) {
            throw new TopicNotFoundException();
        }
    }

    public List<TopicView> getAllApprovedTopics() throws TopicNotFoundException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();

        if (allTopics.getStatusCode().value() != 200 || allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            //TODO throw proper errors for the returned status codes
            throw new TopicNotFoundException();
        } else {
            return allTopics.getBody().stream()
                    .filter(TopicView::getApproved).toList();
        }
    }

    public void addTopic(TopicAddDTO topicAddDTO) throws IOException {
        TopicRequestAddDTO topicRequestAddDTO = mapTopicRequestAddDTO(topicAddDTO);

        String url = applicationTopicsConfiguration.topicAddUrlSource();
        HttpEntity<TopicRequestAddDTO> http = new HttpEntity<>(topicRequestAddDTO);
        ResponseEntity<TopicView> exchange = restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class);

        if (exchange.getStatusCode().value() != 200 || exchange.getBody() == null) {
            //TODO throw proper errors for the returned status codes
            throw new RuntimeException(String.valueOf(exchange.getStatusCode()));
        }
    }

    private TopicRequestAddDTO mapTopicRequestAddDTO(TopicAddDTO topicAddDTO) throws IOException {
        TopicRequestAddDTO topicRequestAddDTO = modelMapper.map(topicAddDTO, TopicRequestAddDTO.class);
        if (topicAddDTO.getPictureFile().isEmpty()) {
            topicRequestAddDTO.setPictureUrl("/images/picture-not-found.jpg");

        } else {
            topicRequestAddDTO.setPictureUrl(cloudService.uploadImage(topicAddDTO.getPictureFile()));
        }

        if (topicRequestAddDTO.getVideoUrl() == null) {
            topicRequestAddDTO.setVideoUrl("MebHynnz0FY");
        }
        return topicRequestAddDTO;
    }

    public TopicDetailsView getTopicDetails(Long id) throws TopicNotFoundException, UserNotFoundException {
        String url = applicationTopicsConfiguration.topicDetailsUrlSource() + "/" + id;
        ResponseEntity<TopicDetailsRequestDTO> topicDetailsRequest = restTemplate
                .exchange(url, HttpMethod.GET, null, TopicDetailsRequestDTO.class);

        if (topicDetailsRequest.getStatusCode().value() != 200 || topicDetailsRequest.getBody() == null) {
            //TODO throw proper errors
            throw new TopicNotFoundException();
        } else {
            return mapTopicDetailsView(topicDetailsRequest.getBody());
        }
    }

    public TopicView getLatestTopic() throws TopicNotFoundException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();
        if (allTopics.getStatusCode().value() != 200 || allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            //TODO throw proper errors
            throw new TopicNotFoundException();
        } else {
            return allTopics.getBody().stream()
                    .filter(TopicView::getApproved)
                    .max((e1, e2) -> {
                        Long id1 = e1.getId();
                        Long id2 = e2.getId();
                        return id1.compareTo(id2);
                    }).orElse(getEmptyTopicView());
        }
    }

    public TopicView getMostCommentedTopic() throws TopicNotFoundException, RuntimeException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();
        if (allTopics.getStatusCode().value() != 200 || allTopics.getBody() == null) {
            //TODO throw proper errors
            throw new RuntimeException("Please try again later.");
        } else {
            List<TopicView> collect = allTopics.getBody()
                    .stream()
                    .filter(TopicView::getApproved)
                    .sorted((e1, e2) -> {
                        Integer count1 = e1.getCommentCount();
                        Integer count2 = e2.getCommentCount();
                        return count1.compareTo(count2);
                    })
                    .toList();
            if (collect.isEmpty()) {
                throw new TopicNotFoundException();
            }
            return collect.get(0);
        }
    }

    public List<TopicView> getAllTopicsByUserId(Long id) throws TopicNotFoundException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();
        if (allTopics.getStatusCode().value() != 200 || allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            //TODO throw proper errors
            throw new TopicNotFoundException();
        } else {
            return allTopics.getBody().stream()
                    .filter(e -> e.getAuthor().equals(id))
                    .collect(Collectors.toList());
        }
    }

    public TopicView getEmptyTopicView() {
        TopicView topicView = new TopicView();
        topicView.setName("No topics");
        topicView.setApproved(true);
        topicView.setId(null);
        topicView.setDescription(null);
        topicView.setPictureUrl("/images/picture-not-found.jpg");
        return topicView;
    }

    public TopicDetailsView mapTopicDetailsView(TopicDetailsRequestDTO topicDetailsRequestDTO) throws UserNotFoundException {
        TopicDetailsView topicDetailsView = modelMapper.map(topicDetailsRequestDTO, TopicDetailsView.class);
        topicDetailsView.setComments(topicDetailsRequestDTO.getComments()
                .stream().map(e -> {
                    try {
                        return mapCommentView(e);
                    } catch (UserNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList()));
        topicDetailsView.setAuthor(userService.getUserById(topicDetailsRequestDTO.getAuthor()).getUsername());
        return topicDetailsView;
    }

    private CommentView mapCommentView(CommentRequestAddDTO commentRequestAddDTO) throws UserNotFoundException {
        CommentView commentView = new CommentView();
        commentView.setContext(commentRequestAddDTO.getContext());
        commentView.setAuthor(userService.getUserById(commentRequestAddDTO.getAuthorId()).getUsername());
        return commentView;
    }
}
