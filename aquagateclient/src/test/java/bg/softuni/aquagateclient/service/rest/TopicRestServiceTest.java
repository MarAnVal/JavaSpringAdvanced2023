package bg.softuni.aquagateclient.service.rest;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicDetailsRequestDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import bg.softuni.aquagateclient.service.rest.util.TopicRestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicRestServiceTest {

    private final TopicRestUtil topicRestUtil;
    private final RestTemplate restTemplate;
    private final TopicRestService topicRestService;
    private TopicAddDTO topicAddDTO;
    private TopicRequestAddDTO topicRequestAddDTO;
    private HttpEntity<TopicRequestAddDTO> http;
    private MultipartFile multipartFile;
    private TopicView topicView;
    private TopicView topicView1;
    private TopicView topicView2;
    private List<TopicView> allTopicViews;
    private List<TopicView> notApprovedTopicViews;
    private final TopicDetailsRequestDTO topicDetailsRequestDTO;

    TopicRestServiceTest() {
        topicRestUtil = mock(TopicRestUtil.class);
        restTemplate = mock(RestTemplate.class);
        topicRestService = new TopicRestService(topicRestUtil, restTemplate);

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

        topicDetailsRequestDTO = new TopicDetailsRequestDTO();
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
    }

    @Test
    void testDoRemoveTopicSuccessful() {
        //Arrange
        TopicView topicView = new TopicView();
        ResponseEntity<TopicView> topicViewResponseEntity = ResponseEntity.ok(topicView);
        String url = "testTopicsAllUrlSource";

        when(topicRestUtil.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestUtil.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.DELETE, null, TopicView.class))
                .thenReturn(topicViewResponseEntity);

        // Act
        ResponseEntity<TopicView> result = topicRestService.doRemoveTopic(id);
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
    void testDoRemoveTopicUnsuccessful404() {
        //Arrange
        String url = "testTopicsAllUrlSource";

        when(topicRestUtil.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestUtil.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.DELETE, null, TopicView.class))
                .thenReturn(ResponseEntity.notFound().build());

        // Act // Assert
        assertThrows(RestClientException.class, () -> topicRestService.doRemoveTopic(id));
    }
    @Test
    void testDoApproveTopicSuccessful(){
        //Arrange
        ResponseEntity<TopicView> topicViewResponseEntity = ResponseEntity.ok(topicView);
        String url = "testTopicsAllUrlSource";

        when(topicRestUtil.topicsAllUrlSource())
                .thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestUtil.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.POST, null, TopicView.class))
                .thenReturn(topicViewResponseEntity);

        // Act
        ResponseEntity<TopicView> result = topicRestService.doApproveTopic(id);
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
    void testDoApproveTopicUnsuccessful() {
        //Arrange
        String url = "testTopicsAllUrlSource";

        when(topicRestUtil.topicsAllUrlSource()).thenReturn(url);

        Long id = 1L;
        String requestUrl = topicRestUtil.topicRemoveUrlSource() + "/" + id;

        when(restTemplate.exchange(requestUrl, HttpMethod.POST, null, TopicView.class))
                .thenReturn(ResponseEntity.notFound().build());

        // Act // Assert
        assertThrows(RestClientException.class, () -> topicRestService.doApproveTopic(id));
    }

    @Test
    void testDoAddTopicSuccessful() {
        // Arrange
        String pictureUrl = "testPictureUrl";
        String url = "testTopicAddUrl";
        TopicView topicView = new TopicView();

        when(topicRestUtil.topicAddUrlSource()).thenReturn(url);
        when(topicRestUtil.getHttpAddTopic(topicAddDTO, pictureUrl))
                .thenReturn(http);
        when(restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class))
                .thenReturn(ResponseEntity.ok(topicView));

        // Act
        ResponseEntity<TopicView> topicViewResponseEntity = topicRestService
                .doAddTopic(topicAddDTO, pictureUrl);
        TopicView body = topicViewResponseEntity.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(topicViewResponseEntity.getStatusCode().value(), 200);
        assertEquals(topicViewResponseEntity.getBody(), topicView);
    }

    @Test
    void testDoAddTopicUnsuccessful() {
        // Arrange
        String pictureUrl = "testPictureUrl";
        String url = "testTopicAddUrl";
        TopicView topicView = new TopicView();
        when(topicRestUtil.topicAddUrlSource()).thenReturn(url);
        when(topicRestUtil.getHttpAddTopic(topicAddDTO, pictureUrl))
                .thenReturn(http);
        when(restTemplate.exchange(url, HttpMethod.POST, http, TopicView.class))
                .thenReturn(ResponseEntity.notFound().build());

        // Act // Assert
        assertThrows(RestClientException.class, () -> topicRestService.doAddTopic(topicAddDTO, pictureUrl));
    }

    @Test
    void testGetTopicDetailsSuccessful(){
        // Arrange
        String url = "testTopicDetailsUrl";
        Long id = 1L;
        when(topicRestUtil.topicDetailsUrlSource()).thenReturn(url);

        when(restTemplate.exchange(url + "/" + id, HttpMethod.GET, null, TopicDetailsRequestDTO.class))
                .thenReturn(ResponseEntity.ok(topicDetailsRequestDTO));

        // Act
        ResponseEntity<TopicDetailsRequestDTO> response = topicRestService.getTopicDetails(id);
        TopicDetailsRequestDTO body = response.getBody();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(body);
        assertEquals(topicDetailsRequestDTO.getId(), body.getId());
        assertEquals(topicDetailsRequestDTO.getLevel(), body.getLevel());
        assertEquals(topicDetailsRequestDTO.getHabitat(), body.getHabitat());
        assertEquals(topicDetailsRequestDTO.getAuthor(), body.getAuthor());
        assertEquals(topicDetailsRequestDTO.getApproved(), body.getApproved());
        assertEquals(topicDetailsRequestDTO.getDescription(), body.getDescription());
        assertEquals(topicDetailsRequestDTO.getName(), body.getName());
        assertEquals(topicDetailsRequestDTO.getVideoUrl(), body.getVideoUrl());
        assertEquals(topicDetailsRequestDTO.getComments().size(), body.getComments().size());
        assertEquals(topicDetailsRequestDTO.getComments().get(0).getContext(),
                body.getComments().get(0).getContext());
    }

    @Test
    void testGetTopicDetailsUnsuccessful(){
        // Arrange
        String url = "testTopicDetailsUrl";
        Long id = 1L;
        when(topicRestUtil.topicDetailsUrlSource()).thenReturn(url);

        when(restTemplate.exchange(url + "/" + id, HttpMethod.GET, null, TopicDetailsRequestDTO.class))
                .thenReturn(ResponseEntity.notFound().build());

        // Act // Assert
        assertThrows(RestClientException.class, () -> topicRestService.getTopicDetails(id));
    }

    @Test
    void testGetAllTopicsSuccessful() {
        // Arrange
        String url = "testTopicDetailsUrl";
        when(topicRestUtil.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestUtil.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);
        List<TopicView> list = List.of(topicView, topicView1, topicView2);
        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestUtil.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.of(Optional.of(list)));
        // Act
        ResponseEntity<List<TopicView>> allTopics = topicRestService.getAllTopics();
        List<TopicView> body = allTopics.getBody();

        // Assert
        assertEquals(200, allTopics.getStatusCode().value());
        assertNotNull(body);
        assertTrue(body.contains(topicView));
        assertTrue(body.contains(topicView1));
        assertTrue(body.contains(topicView2));
    }

    @Test
    void testGetAllTopicsUnsuccessful() {
        // Arrange
        String url = "testTopicDetailsUrl";
        when(topicRestUtil.topicsAllUrlSource()).thenReturn(url);
        ParameterizedTypeReference<List<TopicView>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        when(topicRestUtil.getParameterizedTypeReferenceTopicViewList()).thenReturn(parameterizedTypeReference);

        when(restTemplate
                .exchange(url, HttpMethod.GET, null,
                        topicRestUtil.getParameterizedTypeReferenceTopicViewList())
        )
                .thenReturn(ResponseEntity.notFound().build());
        // Act //Assert
        assertThrows(RestClientException.class, topicRestService::getAllTopics);
    }
}