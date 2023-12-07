package bg.softuni.aquagateclient.service.rest.util;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.service.rest.util.TopicRestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TopicRestUtilTest {
    private final TopicAddDTO topicAddDTO;
    private final TopicRestUtil topicRestUtil;

    TopicRestUtilTest() {
        topicAddDTO = new TopicAddDTO();
        topicAddDTO.setVideoUrl("testVideoUrl");
        topicAddDTO.setName("testName");
        topicAddDTO.setLevel("BEGINNER");
        topicAddDTO.setHabitat("FRESHWATER");
        topicAddDTO.setAuthor(7L);
        topicAddDTO.setDescription("testDescription");

        topicRestUtil = new TopicRestUtil("topicAllUrlSource",
                "topicAddUrlSource",
                "topicDetailsUrlSource",
                "topicRemoveUrlSource",
                "topicApproveUrlSource");
    }

    @Test
    void testGetHttpAddTopic() {
        // Arrange // Act
        HttpEntity<TopicRequestAddDTO> http = topicRestUtil.getHttpAddTopic(topicAddDTO, "testPictureUrl");
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
        assertEquals(topicRestUtil.topicsAllUrlSource(), "topicAllUrlSource");
    }

    @Test
    void testTopicAddUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestUtil.topicAddUrlSource(), "topicAddUrlSource");
    }

    @Test
    void testTopicDetailsUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestUtil.topicDetailsUrlSource(), "topicDetailsUrlSource");
    }

    @Test
    void testTopicRemoveUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestUtil.topicRemoveUrlSource(), "topicRemoveUrlSource");
    }

    @Test
    void testTopicApproveUrlSource() {
        // Arrange // Act // Assert
        assertEquals(topicRestUtil.topicApproveUrlSource(), "topicApproveUrlSource");
    }
}