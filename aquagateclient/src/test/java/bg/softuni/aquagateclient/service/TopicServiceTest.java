package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.rest.TopicRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {
    private final TopicRestService topicRestService;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final TopicService topicService;
    private final TopicAddDTO topicAddDTO;
    private final MultipartFile multipartFile;
    private final TopicView topicView;
    private final TopicView topicView1;
    private final TopicView topicViewNotApproved;
    private final TopicDetailsRequestDTO topicDetailsRequestDTO;
    private final TopicView emptyTopicView;

    TopicServiceTest() {
        topicRestService = mock(TopicRestService.class);
        cloudinaryService = mock(CloudinaryService.class);
        userService = mock(UserService.class);

        topicService = new TopicService(topicRestService, cloudinaryService, userService);

        multipartFile = mock(MultipartFile.class);

        topicAddDTO = new TopicAddDTO();
        topicAddDTO.setLevel("BEGINNER");
        topicAddDTO.setHabitat("FRESHWATER");
        topicAddDTO.setName("to test");
        topicAddDTO.setAuthor(1L);
        topicAddDTO.setDescription("testDescription");
        topicAddDTO.setVideoUrl("MebHynnz0FY");
        topicAddDTO.setPictureFile(multipartFile);

        topicView = new TopicView();
        topicView.setApproved(true);
        topicView.setName("testName");
        topicView.setId(1L);
        topicView.setPictureUrl("testUrl");
        topicView.setDescription("testDescription");
        topicView.setAuthor(7L);
        topicView.setCommentCount(3);

        topicView1 = new TopicView();
        topicView1.setApproved(true);
        topicView1.setName("testName");
        topicView1.setId(2L);
        topicView1.setPictureUrl("testUrl");
        topicView1.setDescription("testDescription");
        topicView1.setAuthor(3L);
        topicView1.setCommentCount(1);

        topicViewNotApproved = new TopicView();
        topicViewNotApproved.setApproved(false);
        topicViewNotApproved.setName("testName");
        topicViewNotApproved.setId(3L);
        topicViewNotApproved.setPictureUrl("testUrl");
        topicViewNotApproved.setDescription("testDescription");
        topicViewNotApproved.setAuthor(7L);
        topicViewNotApproved.setCommentCount(0);

        topicDetailsRequestDTO = new TopicDetailsRequestDTO();
        topicDetailsRequestDTO.setPicture("testPictureUrl");
        topicDetailsRequestDTO.setName("testName");
        topicDetailsRequestDTO.setDescription("testDescription");
        topicDetailsRequestDTO.setHabitat("FRESHWATER");
        topicDetailsRequestDTO.setLevel("BEGINNER");
        topicDetailsRequestDTO.setApproved(true);
        topicDetailsRequestDTO.setAuthor(10L);
        topicDetailsRequestDTO.setId(1L);
        topicDetailsRequestDTO.setVideoUrl("testVideoUr");
        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setAuthorId(11L);
        commentRequestAddDTO.setTopicId(1L);
        commentRequestAddDTO.setContext("do test");
        topicDetailsRequestDTO.setComments(List.of(commentRequestAddDTO));

        emptyTopicView = new TopicView();
        emptyTopicView.setName("No topics");
        emptyTopicView.setApproved(true);
        emptyTopicView.setId(null);
        emptyTopicView.setDescription(null);
        emptyTopicView.setPictureUrl("/images/picture-not-found.jpg");
        emptyTopicView.setCommentCount(0);
    }

    @Test
    void testRemoveTopicSuccessful() throws BadRequestException {
        //Arrange
        TopicView topicView = new TopicView();
        Long id = 5L;
        when(topicRestService.doRemoveTopic(id)).thenReturn(ResponseEntity.ok(topicView));

        // Act
        ResponseEntity<TopicView> result = topicService.removeTopic(id);
        TopicView body = result.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(topicView, body);
    }

    @Test
    void testRemoveTopicUnsuccessful() {
        //Arrange
        Long id = 5L;
        when(topicRestService.doRemoveTopic(id)).thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.removeTopic(id));
    }

    @Test
    void testApproveTopicSuccessful() throws BadRequestException {
        TopicView topicView = new TopicView();
        Long id = 5L;
        when(topicRestService.doApproveTopic(id)).thenReturn(ResponseEntity.ok(topicView));

        // Act
        ResponseEntity<TopicView> result = topicService.approveTopic(id);
        TopicView body = result.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(topicView, body);
    }

    @Test
    void testApproveTopicUnsuccessful() {
        //Arrange
        Long id = 5L;
        when(topicRestService.doApproveTopic(id)).thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.approveTopic(id));
    }

    @Test
    void testAddTopicSuccessfulWithoutPicture() throws IOException, BadRequestException {
        // Arrange
        String pictureUrlNotFoundFile = "/images/picture-not-found.jpg";

        topicAddDTO.setPictureFile(null);
        when(topicRestService.doAddTopic(topicAddDTO, pictureUrlNotFoundFile))
                .thenReturn(ResponseEntity.ok(topicView));

        // Act
        ResponseEntity<TopicView> topicViewResponseEntity = topicService.addTopic(topicAddDTO);
        TopicView body = topicViewResponseEntity.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(topicViewResponseEntity.getStatusCode().value(), 200);
        assertEquals(topicViewResponseEntity.getBody(), topicView);
    }

    @Test
    void testAddTopicSuccessfulWithPicture() throws IOException, BadRequestException {
        // Arrange
        String pictureUrlFoundFile = "/images/picture-not-found-test.jpg";

        topicAddDTO.setVideoUrl(null);

        when(cloudinaryService.uploadImage(multipartFile)).thenReturn(pictureUrlFoundFile);
        when(topicRestService.doAddTopic(topicAddDTO, pictureUrlFoundFile))
                .thenReturn(ResponseEntity.ok(topicView));

        // Act
        ResponseEntity<TopicView> topicViewResponseEntity = topicService.addTopic(topicAddDTO);
        TopicView body = topicViewResponseEntity.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(topicViewResponseEntity.getStatusCode().value(), 200);
        assertEquals(topicViewResponseEntity.getBody(), topicView);
    }

    @Test
    void testAddTopicUnsuccessful() throws IOException {
        // Arrange
        String pictureUrlFoundFile = "/images/picture-not-found-test.jpg";

        when(cloudinaryService.uploadImage(multipartFile)).thenReturn(pictureUrlFoundFile);
        when(topicRestService.doAddTopic(topicAddDTO, pictureUrlFoundFile))
                .thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.addTopic(topicAddDTO));
    }

    @Test
    void testGetTopicDetailsSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);
        when(userService.getUserById(11L)).thenReturn(userEntity);
        when(topicRestService.getTopicDetails(id)).thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

        // Act
        TopicDetailsView topicDetails = topicService.getTopicDetails(id);

        // Assert
        assertEquals(topicDetailsRequestDTO.getId(), topicDetails.getId());
        assertEquals(topicDetailsRequestDTO.getLevel(), topicDetails.getLevel());
        assertEquals(topicDetailsRequestDTO.getHabitat(), topicDetails.getHabitat());
        assertEquals(userEntity.getUsername(), topicDetails.getAuthor());
        assertEquals(topicDetailsRequestDTO.getApproved(), topicDetails.getApproved());
        assertEquals(topicDetailsRequestDTO.getDescription(), topicDetails.getDescription());
        assertEquals(topicDetailsRequestDTO.getName(), topicDetails.getName());
        assertEquals(topicDetailsRequestDTO.getVideoUrl(), topicDetails.getVideoUrl());
        assertEquals(topicDetailsRequestDTO.getComments().size(), topicDetails.getComments().size());
        assertEquals(userEntity.getUsername(), topicDetails.getComments().get(0).getAuthor());
        assertEquals(topicDetailsRequestDTO.getComments().get(0).getContext(),
                topicDetails.getComments().get(0).getContext());
    }

    @Test
    void testGetTopicDetailsUnsuccessful() throws ObjectNotFoundException {
        // Arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);
        when(topicRestService.getTopicDetails(id)).thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.getTopicDetails(id));
    }

    @Test
    void testGetTopicDetailsEmptyBody() throws ObjectNotFoundException {
        // Arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);
        when(topicRestService.getTopicDetails(id)).thenReturn(ResponseEntity.ok(null));

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.getTopicDetails(id));
    }

    @Test
    void testGetTopicDetailsNotFoundCommentAuthorId() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);
        when(userService.getUserById(11L)).thenThrow(ObjectNotFoundException.class);
        when(topicRestService.getTopicDetails(id)).thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

        // Act
        TopicDetailsView topicDetails = topicService.getTopicDetails(id);

        // Assert
        assertEquals(topicDetailsRequestDTO.getId(), topicDetails.getId());
        assertEquals(topicDetailsRequestDTO.getLevel(), topicDetails.getLevel());
        assertEquals(topicDetailsRequestDTO.getHabitat(), topicDetails.getHabitat());
        assertEquals(userEntity.getUsername(), topicDetails.getAuthor());
        assertEquals(topicDetailsRequestDTO.getApproved(), topicDetails.getApproved());
        assertEquals(topicDetailsRequestDTO.getDescription(), topicDetails.getDescription());
        assertEquals(topicDetailsRequestDTO.getName(), topicDetails.getName());
        assertEquals(topicDetailsRequestDTO.getVideoUrl(), topicDetails.getVideoUrl());
        assertEquals(0, topicDetails.getComments().size());
    }

    @Test
    void testGetAllApprovedTopicsGetAllTopicsNullBody() throws BadRequestException {
        // Arrange
        when(topicRestService.getAllTopics()).thenReturn(ResponseEntity.ok(null));

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertEquals(1, allApprovedTopics.size());
        assertEquals(emptyTopicView.getApproved(), allApprovedTopics.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allApprovedTopics.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allApprovedTopics.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allApprovedTopics.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allApprovedTopics.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allApprovedTopics.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allApprovedTopics.get(0).getDescription());
    }

    @Test
    void testGetAllApprovedTopicsGetAllTopicsNotFoundUserId() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView1, topicView, topicViewNotApproved)));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());
        when(userService.getUserById(3L)).thenThrow(ObjectNotFoundException.class);

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertEquals(1, allApprovedTopics.size());
        assertTrue(allApprovedTopics.contains(topicView));
        assertFalse(allApprovedTopics.contains(topicView1));
        assertFalse(allApprovedTopics.contains(topicViewNotApproved));
    }

    @Test
    void testGetAllTopicsUnsuccessful() {
        // Arrange
        when(topicRestService.getAllTopics()).thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, topicService::getAllApprovedTopics);
        assertThrows(BadRequestException.class, topicService::getAllNotApprovedTopics);
        assertThrows(BadRequestException.class, () -> topicService.getAllTopicsByUserId(1L));
    }

    @Test
    void testGetAllApprovedTopicsWithoutApprovedTopics() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicViewNotApproved)));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertEquals(1, allApprovedTopics.size());
        assertEquals(emptyTopicView.getApproved(), allApprovedTopics.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allApprovedTopics.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allApprovedTopics.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allApprovedTopics.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allApprovedTopics.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allApprovedTopics.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allApprovedTopics.get(0).getDescription());
    }

    @Test
    void testGetAllNotApprovedTopics() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView1, topicView, topicViewNotApproved)));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());
        when(userService.getUserById(3L)).thenThrow(ObjectNotFoundException.class);

        // Act
        List<TopicView> allNotApprovedTopics = topicService.getAllNotApprovedTopics();

        //Assert
        assertEquals(1, allNotApprovedTopics.size());
        assertTrue(allNotApprovedTopics.contains(topicViewNotApproved));
    }

    @Test
    void testGetAllNotApprovedTopicsWithoutTopics() throws BadRequestException {
        // Arrange
        when(topicRestService.getAllTopics()).thenReturn(ResponseEntity.ok(null));

        // Act
        List<TopicView> allNotApprovedTopics = topicService.getAllNotApprovedTopics();

        //Assert
        assertEquals(1, allNotApprovedTopics.size());
        assertEquals(emptyTopicView.getApproved(), allNotApprovedTopics.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allNotApprovedTopics.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allNotApprovedTopics.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allNotApprovedTopics.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allNotApprovedTopics.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allNotApprovedTopics.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allNotApprovedTopics.get(0).getDescription());
    }

    @Test
    void testGetAllNotApprovedTopicsWithoutNotApprovedTopics() throws BadRequestException {
        // Arrange
        when(topicRestService.getAllTopics()).thenReturn(ResponseEntity.ok(List.of(topicView1, topicView)));

        // Act
        List<TopicView> allNotApprovedTopics = topicService.getAllNotApprovedTopics();

        //Assert
        assertEquals(1, allNotApprovedTopics.size());
        assertFalse(allNotApprovedTopics.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allNotApprovedTopics.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allNotApprovedTopics.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allNotApprovedTopics.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allNotApprovedTopics.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allNotApprovedTopics.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allNotApprovedTopics.get(0).getDescription());
    }

    @Test
    void testGetLatestTopicSuccessfulWithApprovedTopics() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView1, topicView, topicViewNotApproved)));
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("TestUsername");
        when(userService.getUserById(7L)).thenReturn(userEntity);
        when(userService.getUserById(3L)).thenReturn(userEntity);
        when(userService.getUserById(10L)).thenReturn(userEntity);
        when(userService.getUserById(11L)).thenReturn(userEntity);
        when(topicRestService.getTopicDetails(topicView1.getId())).thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

        // Act
        TopicDetailsView latestTopic = topicService.getLatestTopic();

        // Assert
        assertEquals(topicDetailsRequestDTO.getId(), latestTopic.getId());
    }

    @Test
    void testGetLatestTopicWithoutApprovedTopics() {
        // Arrange
        when(topicRestService.getAllTopics()).thenReturn(ResponseEntity.ok(List.of(topicViewNotApproved)));

        // Act // Assert
        assertThrows(ObjectNotFoundException.class, topicService::getLatestTopic);
    }

    @Test
    void testGetMostCommentedTopic() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView, topicView1, topicViewNotApproved)));
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("TestUsername");
        when(userService.getUserById(7L)).thenReturn(userEntity);
        when(userService.getUserById(3L)).thenReturn(userEntity);

        // Act
        TopicView mostCommentedTopic = topicService.getMostCommentedTopic();

        // Assert
        assertEquals(topicView, mostCommentedTopic);

    }

    @Test
    void testGetMostCommentedTopicGetAllApprovedTopicsUnsuccessful() throws ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicViewNotApproved)));
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("TestUsername");
        when(userService.getUserById(7L)).thenReturn(userEntity);

        // Act // Assert
        assertThrows(ObjectNotFoundException.class, topicService::getMostCommentedTopic);
    }

    @Test
    void testGetAllTopicsByUserIdWithTopics() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView1, topicView, topicViewNotApproved)));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());
        when(userService.getUserById(3L)).thenReturn(new UserEntity());
        Long userId = 3L;

        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(userId);

        // Assert
        assertEquals(1, allTopicsByUserId.size());
        assertTrue(allTopicsByUserId.contains(topicView1));
    }

    @Test
    void testGetAllTopicsByUserIdWithTopicsFromTheUser() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(List.of(topicView1, topicView, topicViewNotApproved)));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());
        when(userService.getUserById(3L)).thenReturn(new UserEntity());
        Long userId = 4L;

        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(userId);

        // Assert
        assertEquals(1, allTopicsByUserId.size());
        assertTrue(allTopicsByUserId.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allTopicsByUserId.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allTopicsByUserId.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allTopicsByUserId.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allTopicsByUserId.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allTopicsByUserId.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allTopicsByUserId.get(0).getDescription());
    }


    @Test
    void testGetAllTopicsByUserIdWithoutTopicsFromTheUser() throws BadRequestException, ObjectNotFoundException {
        // Arrange
        when(topicRestService.getAllTopics())
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));
        when(userService.getUserById(7L)).thenReturn(new UserEntity());
        when(userService.getUserById(3L)).thenReturn(new UserEntity());
        Long userId = 4L;

        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(userId);

        // Assert
        assertEquals(1, allTopicsByUserId.size());
        assertTrue(allTopicsByUserId.get(0).getApproved());
        assertEquals(emptyTopicView.getCommentCount(), allTopicsByUserId.get(0).getCommentCount());
        assertEquals(emptyTopicView.getAuthor(), allTopicsByUserId.get(0).getAuthor());
        assertEquals(emptyTopicView.getName(), allTopicsByUserId.get(0).getName());
        assertEquals(emptyTopicView.getPictureUrl(), allTopicsByUserId.get(0).getPictureUrl());
        assertEquals(emptyTopicView.getId(), allTopicsByUserId.get(0).getId());
        assertEquals(emptyTopicView.getDescription(), allTopicsByUserId.get(0).getDescription());
    }
}