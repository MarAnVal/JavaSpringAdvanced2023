package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicDetailsView;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.service.rest.TopicRestService;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {
    private final TopicRestService topicRestService;
    private final RestTemplate restTemplate;
    private final CloudService cloudService;
    private final UserService userService;
    private final TopicService topicService;
    private TopicAddDTO topicAddDTO;
    private TopicRequestAddDTO topicRequestAddDTO;
    private HttpEntity<TopicRequestAddDTO> http;
    private MultipartFile multipartFile;
    private TopicView topicView;
    private TopicView topicView1;
    private TopicView topicView2;
    private List<TopicView> allTopicViews;
    private List<TopicView> notApprovedTopicViews;

    TopicServiceTest() {
        topicRestService = mock(TopicRestService.class);
        restTemplate = mock(RestTemplate.class);
        cloudService = mock(CloudService.class);
        userService = mock(UserService.class);

        topicService = new TopicService(topicRestService, restTemplate, cloudService, userService);

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

        topicRequestAddDTO = new TopicRequestAddDTO();

        http = new HttpEntity<>(topicRequestAddDTO);

        topicView1 = new TopicView();
        topicView1.setApproved(true);
        topicView1.setName("testName");
        topicView1.setId(2L);
        topicView1.setPictureUrl("testUrl");
        topicView1.setDescription("testDescription");
        topicView1.setAuthor(3L);
        topicView1.setCommentCount(1);

        topicView2 = new TopicView();
        topicView2.setApproved(false);
        topicView2.setName("testName");
        topicView2.setId(3L);
        topicView2.setPictureUrl("testUrl");
        topicView2.setDescription("testDescription");
        topicView2.setAuthor(7L);
        topicView2.setCommentCount(0);

        allTopicViews = new ArrayList<>();
        allTopicViews.add(topicView);
        allTopicViews.add(topicView1);
        allTopicViews.add(topicView2);

        notApprovedTopicViews = new ArrayList<>();
        notApprovedTopicViews.add(topicView2);
    }

    @Test
    void testRemoveTopicSuccessful() throws BadRequestException {
        //Arrange
        TopicView topicView = new TopicView();
        ResponseEntity<TopicView> topicViewResponseEntity = ResponseEntity.ok(topicView);
        String url = "testTopicsAllUrlSource";

        when(topicRestService.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestService.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.DELETE, null, TopicView.class))
                .thenReturn(topicViewResponseEntity);

        // Act
        ResponseEntity<TopicView> result = topicService.removeTopic(id);
        TopicView body = result.getBody();

        // Assert
        assertNotNull(body);
        assertNull(body.getApproved());
        assertNull(body.getName());
        assertNull(body.getId());
        assertNull(body.getAuthor());
        assertNull(body.getDescription());
        assertNull(body.getCommentCount());
        assertNull(body.getPictureUrl());
    }

    @Test
    void testRemoveTopicUnsuccessful() {
        //Arrange
        String url = "testTopicsAllUrlSource";

        when(topicRestService.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestService.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.DELETE, null, TopicView.class))
                .thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.removeTopic(id));
    }

    @Test
    void testApproveTopicSuccessful() throws BadRequestException {
        //Arrange
        ResponseEntity<TopicView> topicViewResponseEntity = ResponseEntity.ok(topicView);
        String url = "testTopicsAllUrlSource";

        when(topicRestService.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestService.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.POST, null, TopicView.class))
                .thenReturn(topicViewResponseEntity);

        // Act
        ResponseEntity<TopicView> result = topicService.approveTopic(id);
        TopicView body = result.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(topicView.getApproved(), body.getApproved());
        assertEquals(topicView.getName(), body.getName());
        assertEquals(topicView.getId(), body.getId());
        assertEquals(topicView.getPictureUrl(), body.getPictureUrl());
        assertEquals(topicView.getDescription(), body.getDescription());
        assertEquals(topicView.getAuthor(), body.getAuthor());
        assertEquals(topicView.getCommentCount(), body.getCommentCount());
    }

    @Test
    void testApproveTopicUnsuccessful() {
        //Arrange
        String url = "testTopicsAllUrlSource";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestService.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.POST, null, TopicView.class))
                .thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.approveTopic(id));
    }

    @Test
    void testAddTopicSuccessfulWithoutPicture() throws IOException, BadRequestException {
        // Arrange
        String pictureUrlNotFoundFile = "/images/picture-not-found.jpg";
        String url = "testTopicAddUrl";
        TopicView topicView = new TopicView();

        topicAddDTO.setPictureFile(null);

        when(topicRestService.topicAddUrlSource()).thenReturn(url);
        when(topicRestService.getHttpAddTopic(topicAddDTO, pictureUrlNotFoundFile))
                .thenReturn(http);
        when(restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class))
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
        String url = "testTopicAddUrl";
        TopicView topicView = new TopicView();

        topicAddDTO.setVideoUrl(null);

        when(cloudService.uploadImage(multipartFile)).thenReturn(pictureUrlFoundFile);
        when(topicRestService.topicAddUrlSource()).thenReturn(url);
        when(topicRestService.getHttpAddTopic(topicAddDTO, pictureUrlFoundFile))
                .thenReturn(http);
        when(restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class))
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
        String url = "testTopicAddUrl";

        when(cloudService.uploadImage(multipartFile)).thenReturn(pictureUrlFoundFile);
        when(topicRestService.topicAddUrlSource()).thenReturn(url);
        when(topicRestService.getHttpAddTopic(topicAddDTO, pictureUrlFoundFile))
                .thenReturn(http);
        when(restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class))
                .thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.addTopic(topicAddDTO));
    }

    @Test
    void testGetTopicDetailsSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";
        Long id = 1L;
        when(topicRestService.topicDetailsUrlSource()).thenReturn(url);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);

        TopicDetailsRequestDTO topicDetailsRequestDTO = getTopicDetailsRequestDTO();

        when(restTemplate.exchange(url + "/" + id, HttpMethod.GET, null, TopicDetailsRequestDTO.class))
                .thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

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

    private static TopicDetailsRequestDTO getTopicDetailsRequestDTO() {
        TopicDetailsRequestDTO topicDetailsRequestDTO = new TopicDetailsRequestDTO();
        topicDetailsRequestDTO.setAuthor(10L);
        topicDetailsRequestDTO.setId(1L);
        topicDetailsRequestDTO.setApproved(true);
        topicDetailsRequestDTO.setLevel("BEGINNER");
        topicDetailsRequestDTO.setName("to test");
        topicDetailsRequestDTO.setDescription("testDescription");
        topicDetailsRequestDTO.setHabitat("FRESHWATER");
        topicDetailsRequestDTO.setVideoUrl("MebHynnz0FY");
        topicDetailsRequestDTO.setPicture("/images/picture-not-found-test.jpg");
        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setAuthorId(10L);
        commentRequestAddDTO.setTopicId(1L);
        commentRequestAddDTO.setContext("do test");
        topicDetailsRequestDTO.setComments(List.of(commentRequestAddDTO));
        return topicDetailsRequestDTO;
    }

    @Test
    void testGetTopicDetailsUnsuccessful() throws ObjectNotFoundException {
        // Arrange
        String url = "testTopicDetailsUrl";
        Long id = 1L;
        when(topicRestService.topicDetailsUrlSource()).thenReturn(url);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);

        when(restTemplate.exchange(url + "/" + id, HttpMethod.GET, null, TopicDetailsRequestDTO.class))
                .thenThrow(RestClientException.class);

        // Act // Assert
        assertThrows(BadRequestException.class, () -> topicService.getTopicDetails(id));
    }

    @Test
    void testGetLatestTopicSuccessful() throws ObjectNotFoundException, BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));

        Long id = 3L;

        when(topicRestService.topicDetailsUrlSource()).thenReturn(url);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");

        when(userService.getUserById(10L)).thenReturn(userEntity);

        TopicDetailsRequestDTO topicDetailsRequestDTO = getTopicDetailsRequestDTO();

        when(restTemplate.exchange("testTopicDetailsUrl" + "/" + id, HttpMethod.GET, null, TopicDetailsRequestDTO.class))
                .thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

        // Act
        TopicDetailsView latestTopic = topicService.getLatestTopic();

        // Assert
        assertEquals(topicDetailsRequestDTO.getId(), latestTopic.getId());
    }

    @Test
    void testGetLatestTopicUnsuccessful() {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList()))
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));

        // Act // Assert
        assertThrows(ObjectNotFoundException.class, topicService::getLatestTopic);
    }

    @Test
    void testGetMostCommentedTopicSuccessfulWithApprovedTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));

        // Act
        TopicView mostCommentedTopic = topicService.getMostCommentedTopic();

        // Assert
        assertEquals(topicView.getId(), mostCommentedTopic.getId());
    }

    @Test
    void testGetMostCommentedTopicSuccessfulWithoutApprovedTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(notApprovedTopicViews));

        // Act
        TopicView mostCommentedTopic = topicService.getMostCommentedTopic();

        // Assert
        assertNull(mostCommentedTopic.getId());
        assertEquals("No topics", mostCommentedTopic.getName());
        assertEquals(true, mostCommentedTopic.getApproved());
        assertNull(mostCommentedTopic.getDescription());
        assertEquals("/images/picture-not-found.jpg", mostCommentedTopic.getPictureUrl());
    }

    @Test
    void testGetMostCommentedTopicSuccessfulWithoutTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));

        // Act
        TopicView mostCommentedTopic = topicService.getMostCommentedTopic();

        // Assert
        assertNull(mostCommentedTopic.getId());
        assertEquals("No topics", mostCommentedTopic.getName());
        assertEquals(true, mostCommentedTopic.getApproved());
        assertNull(mostCommentedTopic.getDescription());
        assertEquals("/images/picture-not-found.jpg", mostCommentedTopic.getPictureUrl());
    }

    @Test
    void testGetAllNotApprovedTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));

        // Act
        List<TopicView> allNotApprovedTopics = topicService.getAllNotApprovedTopics();

        //Assert
        assertEquals(1, allNotApprovedTopics.size());
        assertEquals("testName", allNotApprovedTopics.get(0).getName());
        assertEquals(3L, allNotApprovedTopics.get(0).getId());
        assertEquals("testUrl", allNotApprovedTopics.get(0).getPictureUrl());
        assertEquals("testDescription", allNotApprovedTopics.get(0).getDescription());
        assertEquals(7L, allNotApprovedTopics.get(0).getAuthor());
        assertEquals(0, allNotApprovedTopics.get(0).getCommentCount());
    }

    @Test
    void testGetAllNotApprovedTopicsWithoutTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));

        // Act
        List<TopicView> allNotApprovedTopics = topicService.getAllNotApprovedTopics();

        //Assert
        assertEquals(1, allNotApprovedTopics.size());
        assertNull(allNotApprovedTopics.get(0).getId());
        assertEquals("No topics", allNotApprovedTopics.get(0).getName());
        assertEquals(true, allNotApprovedTopics.get(0).getApproved());
        assertNull(allNotApprovedTopics.get(0).getDescription());
        assertEquals("/images/picture-not-found.jpg", allNotApprovedTopics.get(0).getPictureUrl());
    }

    @Test
    void testGetAllApprovedTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertEquals(2, allApprovedTopics.size());
        assertTrue(allApprovedTopics.contains(topicView));
        assertTrue(allApprovedTopics.contains(topicView1));
    }

    @Test
    void testGetAllApprovedTopicsWithoutApprovedTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(notApprovedTopicViews));

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertNull(allApprovedTopics.get(0).getId());
        assertEquals("No topics", allApprovedTopics.get(0).getName());
        assertEquals(true, allApprovedTopics.get(0).getApproved());
        assertNull(allApprovedTopics.get(0).getDescription());
        assertEquals("/images/picture-not-found.jpg", allApprovedTopics.get(0).getPictureUrl());
    }

    @Test
    void testGetAllApprovedTopicsWithoutTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));

        // Act
        List<TopicView> allApprovedTopics = topicService.getAllApprovedTopics();

        // Assert
        assertEquals(1, allApprovedTopics.size());
        assertNull(allApprovedTopics.get(0).getId());
        assertEquals("No topics", allApprovedTopics.get(0).getName());
        assertEquals(true, allApprovedTopics.get(0).getApproved());
        assertNull(allApprovedTopics.get(0).getDescription());
        assertEquals("/images/picture-not-found.jpg", allApprovedTopics.get(0).getPictureUrl());
    }

    @Test
    void testGetAllTopicsByUserId() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));
        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(3L);

        // Assert
        assertEquals(1, allTopicsByUserId.size());
        assertTrue(allTopicsByUserId.contains(topicView1));
    }

    @Test
    void testGetAllTopicsByUserIdWithoutUsersTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(allTopicViews));
        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(100L);

        //Assert
        assertEquals(1, allTopicsByUserId.size());
        assertNull(allTopicsByUserId.get(0).getId());
        assertEquals("No topics", allTopicsByUserId.get(0).getName());
        assertEquals(true, allTopicsByUserId.get(0).getApproved());
        assertNull(allTopicsByUserId.get(0).getDescription());
        assertEquals("/images/picture-not-found.jpg", allTopicsByUserId.get(0).getPictureUrl());
    }

    @Test
    void testGetAllTopicsByUserIdWithoutTopics() throws BadRequestException {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.ok(new ArrayList<>()));
        // Act
        List<TopicView> allTopicsByUserId = topicService.getAllTopicsByUserId(1L);

        //Assert
        assertEquals(1, allTopicsByUserId.size());
        assertNull(allTopicsByUserId.get(0).getId());
        assertEquals("No topics", allTopicsByUserId.get(0).getName());
        assertEquals(true, allTopicsByUserId.get(0).getApproved());
        assertNull(allTopicsByUserId.get(0).getDescription());
        assertEquals("/images/picture-not-found.jpg", allTopicsByUserId.get(0).getPictureUrl());
    }

    @Test
    void testGetAllTopicsUnsuccessful() {
        // Arrange
        String url = "testTopicDetailsUrl";

        when(topicRestService.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestService.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestService.getParameterizedTypeReferenceTopicViewList())
        )
                .thenThrow(RestClientException.class);
        // Act //Assert
        assertThrows(BadRequestException.class, () -> topicService.getAllTopicsByUserId(3L));
        assertThrows(BadRequestException.class, topicService::getAllApprovedTopics);
        assertThrows(BadRequestException.class, topicService::getAllNotApprovedTopics);
        assertThrows(BadRequestException.class, topicService::getLatestTopic);
        assertThrows(BadRequestException.class, topicService::getMostCommentedTopic);
    }
}