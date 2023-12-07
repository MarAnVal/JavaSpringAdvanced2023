package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.view.CommentView;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.rest.TopicRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    private final TopicRestService topicRestService;
    private final CloudService cloudService;
    private final UserService userService;

    public TopicService(TopicRestService topicRestService, CloudService cloudService, UserService userService) {
        this.topicRestService = topicRestService;
        this.cloudService = cloudService;
        this.userService = userService;
    }

    public ResponseEntity<TopicView> removeTopic(Long id) throws BadRequestException {
        try {
            return topicRestService.doRemoveTopic(id);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    private List<TopicView> getAllTopics() throws BadRequestException {
        try {
            ResponseEntity<List<TopicView>> allTopics = topicRestService.getAllTopics();

            if (allTopics.getBody() == null || allTopics.getBody().isEmpty()) {
                return new ArrayList<>();

            } else {
                List<TopicView> returnedTopics = new ArrayList<>();
                List<TopicView> topics = allTopics.getBody();
                for (TopicView topicView : topics) {
                    try {
                        userService.getUserById(topicView.getAuthor());
                        returnedTopics.add(topicView);
                    } catch (ObjectNotFoundException ignored) {

                    }
                }

                return returnedTopics;
            }

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public List<TopicView> getAllApprovedTopics() throws BadRequestException {
        List<TopicView> allTopics = getAllTopics();

        if (allTopics.isEmpty()) {

            return List.of(getEmptyTopicView());
        } else {

            List<TopicView> approved = allTopics.stream()
                    .filter(TopicView::getApproved).toList();
            if (approved.isEmpty()) {
                return List.of(getEmptyTopicView());
            }

            return approved;
        }
    }

    public List<TopicView> getAllNotApprovedTopics() throws BadRequestException {
        List<TopicView> allTopics = getAllTopics();

        if (allTopics.isEmpty()) {
            return List.of(getEmptyTopicView());
        } else {
            List<TopicView> notApproved = allTopics.stream()
                    .filter(e -> !e.getApproved()).toList();
            if (notApproved.isEmpty()) {
                TopicView emptyTopicView = getEmptyTopicView();
                emptyTopicView.setApproved(false);
                return List.of(emptyTopicView);
            }

            return notApproved;
        }
    }

    public ResponseEntity<TopicView> approveTopic(Long id) throws BadRequestException {
        try {
            return topicRestService.doApproveTopic(id);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }

    }

    public ResponseEntity<TopicView> addTopic(TopicAddDTO topicAddDTO) throws IOException, BadRequestException {

        String pictureUrl = "/images/picture-not-found.jpg";

        if (topicAddDTO.getPictureFile() != null) {
            pictureUrl = cloudService.uploadImage(topicAddDTO.getPictureFile());
        }

        if (topicAddDTO.getVideoUrl() == null) {
            topicAddDTO.setVideoUrl("MebHynnz0FY");
        }

        try {
            return topicRestService.doAddTopic(topicAddDTO, pictureUrl);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicDetailsView getTopicDetails(Long id) throws BadRequestException, ObjectNotFoundException {
        try {
            ResponseEntity<TopicDetailsRequestDTO> topicDetails = topicRestService.getTopicDetails(id);
            TopicDetailsRequestDTO body = topicDetails.getBody();
            return mapTopicDetailsView(body);

        } catch (RestClientException e) {
            throw new BadRequestException("Please try again later!");
        }
    }

    public TopicDetailsView getLatestTopic() throws ObjectNotFoundException, BadRequestException {
        List<TopicView> allTopics = getAllApprovedTopics();
        if (allTopics.size() == 1 && allTopics.get(0).getId() == null) {
            throw new ObjectNotFoundException("Topic not found!");
        }
        Long id = allTopics.stream()
                .map(TopicView::getId)
                .max(Long::compareTo)
                .orElse(null);

        return getTopicDetails(id);
    }

    public TopicView getMostCommentedTopic() throws BadRequestException, ObjectNotFoundException {
        List<TopicView> allTopics = getAllApprovedTopics();
        if (allTopics.size() == 1 && allTopics.get(0).getId() == null) {
            throw new ObjectNotFoundException("Topic not found!");
        }

        return allTopics.stream()
                .sorted((e1, e2) -> {
                    Integer count1 = e1.getCommentCount();
                    Integer count2 = e2.getCommentCount();
                    return count2.compareTo(count1);
                })
                .toList()
                .get(0);
    }

    public List<TopicView> getAllTopicsByUserId(Long id) throws BadRequestException {

        List<TopicView> allTopics = getAllApprovedTopics();
        if (allTopics.size() == 1 && allTopics.get(0).getId() == null) {
            return List.of(getEmptyTopicView());
        }

        List<TopicView> list = allTopics.stream()
                .filter(e -> e.getAuthor().equals(id))
                .toList();

        if (list.isEmpty()) {
            return List.of(getEmptyTopicView());
        }
        return list;

    }

    private TopicView getEmptyTopicView() {
        TopicView topicView = new TopicView();
        topicView.setName("No topics");
        topicView.setApproved(true);
        topicView.setId(null);
        topicView.setDescription(null);
        topicView.setPictureUrl("/images/picture-not-found.jpg");
        topicView.setCommentCount(0);
        topicView.setAuthor(null);
        return topicView;
    }

    private TopicDetailsView mapTopicDetailsView(TopicDetailsRequestDTO topicDetailsRequestDTO)
            throws BadRequestException, ObjectNotFoundException {

        if (topicDetailsRequestDTO == null) {
            throw new BadRequestException("Please try again later!");

        } else {

            TopicDetailsView topicDetailsView = getTopicDetailsView(topicDetailsRequestDTO);

            List<CommentView> commentViews = new ArrayList<>();
            for (CommentRequestAddDTO comment : topicDetailsRequestDTO.getComments()) {
                CommentView commentView = mapCommentView(comment);
                if (commentView != null) {
                    commentViews.add(commentView);
                }
            }
            topicDetailsView.setComments(commentViews);

            topicDetailsView.setAuthor(userService.getUserById(topicDetailsRequestDTO.getAuthor()).getUsername());

            return topicDetailsView;
        }
    }

    private static TopicDetailsView getTopicDetailsView(TopicDetailsRequestDTO topicDetailsRequestDTO) {
        TopicDetailsView topicDetailsView = new TopicDetailsView();
        topicDetailsView.setHabitat(topicDetailsRequestDTO.getHabitat());
        topicDetailsView.setLevel(topicDetailsRequestDTO.getLevel());
        topicDetailsView.setApproved(topicDetailsRequestDTO.getApproved());
        topicDetailsView.setDescription(topicDetailsRequestDTO.getDescription());
        topicDetailsView.setId(topicDetailsRequestDTO.getId());
        topicDetailsView.setPicture(topicDetailsRequestDTO.getPicture());
        topicDetailsView.setName(topicDetailsRequestDTO.getName());
        topicDetailsView.setVideoUrl(topicDetailsRequestDTO.getVideoUrl());
        return topicDetailsView;
    }

    private CommentView mapCommentView(CommentRequestAddDTO commentRequestAddDTO) {
        CommentView commentView = new CommentView();
        commentView.setContext(commentRequestAddDTO.getContext());
        try {
            commentView.setAuthor(userService.getUserById(commentRequestAddDTO.getAuthorId()).getUsername());
        } catch (ObjectNotFoundException e) {
            return null;
        }
        return commentView;
    }
}
