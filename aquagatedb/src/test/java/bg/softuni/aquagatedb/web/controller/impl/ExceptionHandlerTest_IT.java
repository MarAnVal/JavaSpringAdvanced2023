package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.repository.HabitatRepo;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.service.HabitatService;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerTest_IT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private PictureRepo pictureRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private HabitatService habitatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        topicRepo.deleteAll();
        pictureRepo.deleteAll();
        commentRepo.deleteAll();

        habitatService.init();
    }

    @Test
    void testDoCommentAdd404ExceptionHandlerResponseNoSuchTopicToAddCommentToIt() throws Exception {
        // Arrange
        CommentAddDTO commentAddDTO200 = new CommentAddDTO();
        commentAddDTO200.setTopicId(2L);
        commentAddDTO200.setAuthorId(1L);
        commentAddDTO200.setContext("to do test");
        String json = objectMapper.writeValueAsString(commentAddDTO200);

        // Act // Assert
        assertEquals(0, topicRepo.count());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comments/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void testGetTopicDetails404ExceptionHandlerResponseNoSuchTopicToGetDetails() throws Exception {
        // Arrange // Act // Assert
        assertEquals(0, topicRepo.count());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/details/1")
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void testDoRemove404ExceptionHandlerResponseNoTopicWithTheId() throws Exception {
        // Arrange // Act // Assert
        assertEquals(0, topicRepo.count());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/remove/1")
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void testDoApprove404ExceptionHandlerResponseNoTopicWithTheId() throws Exception {
        // Arrange // Act // Assert
        assertEquals(0, topicRepo.count());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/topics/approve/1")
        ).andExpect(status().is4xxClientError());
    }
}
