package bg.softuni.aquagateclient.service.rest;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TopicRestServiceTest {
    private final TopicAddDTO topicAddDTO;
    private final TopicRestService topicRestService;

    TopicRestServiceTest() {
        topicAddDTO = new TopicAddDTO();
        topicAddDTO.setVideoUrl("testVideoUrl");
        topicAddDTO.setName("testName");
        topicAddDTO.setLevel("BEGINNER");
        topicAddDTO.setHabitat("FRESHWATER");
        topicAddDTO.setAuthor(7L);
        topicAddDTO.setDescription("testDescription");

        topicRestService = new TopicRestService("topicAllUrlSource",
                "topicAddUrlSource",
                "topicDetailsUrlSource",
                "topicRemoveUrlSource",
                "topicApproveUrlSource");
    }

    @Test
    void testGetHttpAddTopic() {
        // Arrange // Act
        HttpEntity<TopicRequestAddDTO> http = topicRestService.getHttpAddTopic(topicAddDTO, "testPictureUrl");
        TopicRequestAddDTO body = http.getBody();

        // Assert
        assertNotNull(body);
        assertEquals(topicAddDTO.getVideoUrl(), body.getVideoUrl()) ;
        assertEquals(topicAddDTO.getName(), body.getName());
        assertEquals(topicAddDTO.getLevel(), body.getLevel());
        assertEquals(topicAddDTO.getHabitat(), body.getHabitat());
        assertEquals(topicAddDTO.getAuthor(), body.getAuthor());
        assertEquals(topicAddDTO.getDescription(), body.getDescription());
        assertEquals("testPictureUrl", body.getPictureUrl());
    }

    @Test
    void testTopicsAllUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestService.topicsAllUrlSource(), "topicAllUrlSource");
    }

    @Test
    void testTopicAddUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestService.topicAddUrlSource(), "topicAddUrlSource");
    }

    @Test
    void testTopicDetailsUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestService.topicDetailsUrlSource(), "topicDetailsUrlSource");
    }

    @Test
    void testTopicRemoveUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestService.topicRemoveUrlSource(), "topicRemoveUrlSource");
    }

    @Test
    void testTopicApproveUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestService.topicApproveUrlSource(), "topicApproveUrlSource");
    }
}