package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationTopicsConfiguration;
import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
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


    private ResponseEntity<List<TopicView>> getAllTopics() throws BadRequestException {
        try {
            String url = applicationTopicsConfiguration.topicsAllUrlSource();

            return restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TopicView>>() {
                    });

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public void removeTopic(Long id) throws BadRequestException {
        try {
            String url = applicationTopicsConfiguration.topicRemoveUrlSource() + "/" + id;
            restTemplate.exchange(url, HttpMethod.DELETE, null, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public List<TopicView> getAllNotApprovedTopics() throws BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = getAllTopics();

            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
                return List.of(getEmptyTopicView());
            } else {
                return allTopics.getBody()
                        .stream()
                        .filter(e -> !e.getApproved()).collect(Collectors.toList());
            }

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public void approveTopic(Long id) throws BadRequestException {
        try {
            String url = applicationTopicsConfiguration.topicApproveUrlSource() + "/" + id;
            ResponseEntity<TopicView> exchange = restTemplate.exchange(url, HttpMethod.POST, null, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public List<TopicView> getAllApprovedTopics() throws BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = getAllTopics();

            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
                return List.of(getEmptyTopicView());

            } else {
                return allTopics.getBody().stream()
                        .filter(TopicView::getApproved).toList();
            }
        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public void addTopic(TopicAddDTO topicAddDTO) throws IOException, BadRequestException {
        try {
            TopicRequestAddDTO topicRequestAddDTO = mapTopicRequestAddDTO(topicAddDTO);

            String url = applicationTopicsConfiguration.topicAddUrlSource();
            HttpEntity<TopicRequestAddDTO> http = new HttpEntity<>(topicRequestAddDTO);

            ResponseEntity<TopicView> exchange = restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
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

    public TopicDetailsView getTopicDetails(Long id) throws BadRequestException, ObjectNotFoundException {
        try {
            String url = applicationTopicsConfiguration.topicDetailsUrlSource() + "/" + id;

            ResponseEntity<TopicDetailsRequestDTO> topicDetailsRequest = restTemplate
                    .exchange(url, HttpMethod.GET, null, TopicDetailsRequestDTO.class);

            return mapTopicDetailsView(topicDetailsRequest.getBody());

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicDetailsView getLatestTopic() throws ObjectNotFoundException, BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = getAllTopics();
            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
                throw new ObjectNotFoundException("Topic not found!");
            } else {
                Long id = allTopics.getBody().stream()
                        .map(TopicView::getId)
                        .max(Long::compareTo)
                        .orElse(null);

                return getTopicDetails(id);
            }
        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicView getMostCommentedTopic() throws BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = getAllTopics();

            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
                return getEmptyTopicView();

            } else {

                List<TopicView> list = allTopics.getBody()
                        .stream()
                        .filter(TopicView::getApproved)
                        .sorted((e1, e2) -> {
                            Integer count1 = e1.getCommentCount();
                            Integer count2 = e2.getCommentCount();
                            return count1.compareTo(count2);
                        })
                        .toList();
                if(list.isEmpty()){
                    return getEmptyTopicView();
                }
                return list.get(0);
            }
        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public List<TopicView> getAllTopicsByUserId(Long id) throws BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = getAllTopics();
            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {

                return List.of(getEmptyTopicView());
            } else {
                return allTopics.getBody().stream()
                        .filter(e -> e.getAuthor().equals(id))
                        .collect(Collectors.toList());
            }
        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
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

    public TopicDetailsView mapTopicDetailsView(TopicDetailsRequestDTO topicDetailsRequestDTO)
            throws ObjectNotFoundException {

        TopicDetailsView topicDetailsView = modelMapper.map(topicDetailsRequestDTO, TopicDetailsView.class);

        List<CommentView> commentViews = new ArrayList<>();
        for (CommentRequestAddDTO comment : topicDetailsRequestDTO.getComments()) {
            CommentView commentView = mapCommentView(comment);
            commentViews.add(commentView);
        }
        topicDetailsView.setComments(commentViews);

        topicDetailsView.setAuthor(userService.getUserById(topicDetailsRequestDTO.getAuthor()).getUsername());

        return topicDetailsView;
    }

    private CommentView mapCommentView(CommentRequestAddDTO commentRequestAddDTO) throws ObjectNotFoundException {
        CommentView commentView = new CommentView();
        commentView.setContext(commentRequestAddDTO.getContext());
        commentView.setAuthor(userService.getUserById(commentRequestAddDTO.getAuthorId()).getUsername());
        return commentView;
    }
}
