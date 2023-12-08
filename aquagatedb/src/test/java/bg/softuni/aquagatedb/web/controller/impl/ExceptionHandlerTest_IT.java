package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerTest_IT {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testDoCommentAdd404ExceptionHandlerResponse() throws Exception {
        // Arrange
        CommentAddDTO commentAddDTO200 = new CommentAddDTO();
        commentAddDTO200.setTopicId(2L);
        commentAddDTO200.setAuthorId(1L);
        commentAddDTO200.setContext("to do test");
        String json = objectMapper.writeValueAsString(commentAddDTO200);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comments/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is4xxClientError());
    }
}
