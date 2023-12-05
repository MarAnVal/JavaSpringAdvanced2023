package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.rest.TopicRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
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
    private final TopicRestService topicRestService;
    private final RestTemplate restTemplate;
    private final CloudService cloudService;
    private final UserService userService;

    public TopicService(TopicRestService topicRestService, RestTemplate restTemplate,
                        CloudService cloudService, UserService userService) {
        this.topicRestService = topicRestService;
        this.restTemplate = restTemplate;
        this.cloudService = cloudService;
        this.userService = userService;
    }

    private ResponseEntity<List<TopicView>> getAllTopics() throws BadRequestException {
        try {
            String url = topicRestService.topicsAllUrlSource();

            return restTemplate
                    .exchange(url, HttpMethod.GET, null,
                            topicRestService.getParameterizedTypeReferenceTopicViewList());

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public ResponseEntity<TopicView> removeTopic(Long id) throws BadRequestException {
        try {
            String url = topicRestService.topicRemoveUrlSource() + "/" + id;
            return restTemplate.exchange(url, HttpMethod.DELETE, null, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public List<TopicView> getAllNotApprovedTopics() throws BadRequestException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();

        if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            return List.of(getEmptyTopicView());
        } else {
            return allTopics.getBody()
                    .stream()
                    .filter(e -> !e.getApproved()).collect(Collectors.toList());
        }
    }

    public ResponseEntity<TopicView> approveTopic(Long id) throws BadRequestException {
        try {
            String url = topicRestService.topicApproveUrlSource() + "/" + id;
            return restTemplate.exchange(url, HttpMethod.POST, null, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public List<TopicView> getAllApprovedTopics() throws BadRequestException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();

        if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            return List.of(getEmptyTopicView());

        } else {
            List<TopicView> list = allTopics.getBody().stream()
                    .filter(TopicView::getApproved).toList();

            if (list.isEmpty()) {
                return List.of(getEmptyTopicView());
            }
            return list;
        }
    }

    public ResponseEntity<TopicView> addTopic(TopicAddDTO topicAddDTO) throws IOException, BadRequestException {
        String url = topicRestService.topicAddUrlSource();
        String pictureUrl = "/images/picture-not-found.jpg";

        if (topicAddDTO.getPictureFile() != null) {
            pictureUrl = cloudService.uploadImage(topicAddDTO.getPictureFile());
        }

        if (topicAddDTO.getVideoUrl() == null) {
            topicAddDTO.setVideoUrl("MebHynnz0FY");
        }

        HttpEntity<TopicRequestAddDTO> http = topicRestService.getHttpAddTopic(topicAddDTO, pictureUrl);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicDetailsView getTopicDetails(Long id) throws BadRequestException, ObjectNotFoundException {
        try {
            String url = topicRestService.topicDetailsUrlSource() + "/" + id;

            ResponseEntity<TopicDetailsRequestDTO> topicDetailsRequest = restTemplate
                    .exchange(url, HttpMethod.GET, null, TopicDetailsRequestDTO.class);

            return mapTopicDetailsView(topicDetailsRequest.getBody());

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicDetailsView getLatestTopic() throws ObjectNotFoundException, BadRequestException {
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
    }

    public TopicView getMostCommentedTopic() throws BadRequestException {

        ResponseEntity<List<TopicView>> allTopics = getAllTopics();

        if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
            return getEmptyTopicView();

        } else {

            List<TopicView> approved = allTopics.getBody()
                    .stream()
                    .filter(TopicView::getApproved)
                    .toList();

            if (approved.isEmpty()) {
                return getEmptyTopicView();

            } else {
                return approved.stream()
                        .sorted((e1, e2) -> {
                            Integer count1 = e1.getCommentCount();
                            Integer count2 = e2.getCommentCount();
                            return count2.compareTo(count1);
                        })
                        .toList()
                        .get(0);
            }
        }
    }

    public List<TopicView> getAllTopicsByUserId(Long id) throws BadRequestException {
        ResponseEntity<List<TopicView>> allTopics = getAllTopics();
        if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {

            return List.of(getEmptyTopicView());
        } else {
            List<TopicView> list = allTopics.getBody().stream()
                    .filter(e -> e.getAuthor().equals(id))
                    .toList();
            if (list.isEmpty()) {
                return List.of(getEmptyTopicView());
            }
            return list;
        }
    }

    private TopicView getEmptyTopicView() {
        TopicView topicView = new TopicView();
        topicView.setName("No topics");
        topicView.setApproved(true);
        topicView.setId(null);
        topicView.setDescription(null);
        topicView.setPictureUrl("/images/picture-not-found.jpg");
        return topicView;
    }

    private TopicDetailsView mapTopicDetailsView(TopicDetailsRequestDTO topicDetailsRequestDTO)
            throws ObjectNotFoundException, BadRequestException {
        if (topicDetailsRequestDTO == null) {
            throw new BadRequestException("Please try again later!");
        } else {
            TopicDetailsView topicDetailsView = new TopicDetailsView();
            topicDetailsView.setHabitat(topicDetailsRequestDTO.getHabitat());
            topicDetailsView.setLevel(topicDetailsRequestDTO.getLevel());
            topicDetailsView.setApproved(topicDetailsRequestDTO.getApproved());
            topicDetailsView.setDescription(topicDetailsRequestDTO.getDescription());
            topicDetailsView.setId(topicDetailsRequestDTO.getId());
            topicDetailsView.setPicture(topicDetailsRequestDTO.getPicture());
            topicDetailsView.setName(topicDetailsRequestDTO.getName());
            topicDetailsView.setVideoUrl(topicDetailsRequestDTO.getVideoUrl());

            List<CommentView> commentViews = new ArrayList<>();
            for (CommentRequestAddDTO comment : topicDetailsRequestDTO.getComments()) {
                CommentView commentView = mapCommentView(comment);
                commentViews.add(commentView);
            }
            topicDetailsView.setComments(commentViews);

            topicDetailsView.setAuthor(userService.getUserById(topicDetailsRequestDTO.getAuthor()).getUsername());

            return topicDetailsView;
        }
    }

    private CommentView mapCommentView(CommentRequestAddDTO commentRequestAddDTO) throws ObjectNotFoundException {
        CommentView commentView = new CommentView();
        commentView.setContext(commentRequestAddDTO.getContext());
        commentView.setAuthor(userService.getUserById(commentRequestAddDTO.getAuthorId()).getUsername());
        return commentView;
    }
}
