package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.service.HabitatService;
import bg.softuni.aquagatedb.service.PictureService;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentsControllerImplTest_IT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private HabitatService habitatService;
    @Autowired
    private PictureRepo pictureRepo;
    @Autowired
    private PictureService pictureService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() throws ObjectNotFoundException {
        commentRepo.deleteAll();
        topicRepo.deleteAll();
        pictureRepo.deleteAll();

        habitatService.init();

        Topic topic = new Topic();
        topic.setComments(new ArrayList<>());
        topic.setDate(LocalDate.now(ZoneOffset.UTC));
        topic.setApproved(false);
        topic.setName("To do test");
        Habitat habitat = habitatService.findHabitatByName("FRESHWATER");
        topic.setHabitat(habitat);
        topic.setLevel(LevelEnum.BEGINNER);
        topic.setDescription("to do test topic");
        topic.setAuthor(2L);
        topic.setVideoUrl("testVideoUrl");
        Picture picture = pictureService.addPicture("testPictureUrl.bnp");
        topic.setPicture(picture);
        topic.setComments(new ArrayList<>());

        topicRepo.save(topic);
    }

    @Test
    void testDoCommentAdd200Response() throws Exception {
        // Arrange
        CommentAddDTO commentAddDTO200 = new CommentAddDTO();
        commentAddDTO200.setTopicId(1L);
        commentAddDTO200.setAuthorId(1L);
        commentAddDTO200.setContext("to do test");
        String json = objectMapper.writeValueAsString(commentAddDTO200);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comments/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is2xxSuccessful());

        assertEquals(1, commentRepo.count());
        assertTrue(commentRepo.findById(1L).isPresent());
        assertEquals(commentAddDTO200.getContext(), commentRepo.findById(1L).get().getContext());
        assertEquals(commentAddDTO200.getAuthorId(), commentRepo.findById(1L).get().getAuthorId());
        assertEquals(commentAddDTO200.getTopicId(), commentRepo.findById(1L).get().getTopic().getId());

    }

    @ParameterizedTest
    @MethodSource
    void testDoCommentAdd422Response(CommentAddDTO commentAddDTO) throws Exception {
        // Arrange
        String json = objectMapper.writeValueAsString(commentAddDTO);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comments/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is4xxClientError());
    }

    static Stream<CommentAddDTO> testDoCommentAdd422Response() {
        CommentAddDTO commentAddDTONullAuthor = new CommentAddDTO();
        commentAddDTONullAuthor.setTopicId(1L);
        commentAddDTONullAuthor.setAuthorId(null);
        commentAddDTONullAuthor.setContext("to do test");

        CommentAddDTO commentAddDTONullTopic = new CommentAddDTO();
        commentAddDTONullTopic.setTopicId(null);
        commentAddDTONullTopic.setAuthorId(1L);
        commentAddDTONullTopic.setContext("to do test");

        CommentAddDTO commentAddDTO2LettersContext = new CommentAddDTO();
        commentAddDTO2LettersContext.setTopicId(1L);
        commentAddDTO2LettersContext.setAuthorId(1L);
        commentAddDTO2LettersContext.setContext("to");

        CommentAddDTO commentAddDTO4LettersContext = new CommentAddDTO();
        commentAddDTO4LettersContext.setTopicId(1L);
        commentAddDTO4LettersContext.setAuthorId(1L);
        commentAddDTO4LettersContext.setContext("todo");

        CommentAddDTO commentAddDTONullContext = new CommentAddDTO();
        commentAddDTONullContext.setTopicId(1L);
        commentAddDTONullContext.setAuthorId(1L);
        commentAddDTONullContext.setContext(null);

        CommentAddDTO commentAddDTOTooLongContext = new CommentAddDTO();
        commentAddDTOTooLongContext.setTopicId(1L);
        commentAddDTOTooLongContext.setAuthorId(1L);
        commentAddDTOTooLongContext.setContext("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        return Stream.of(commentAddDTONullAuthor, commentAddDTONullTopic, commentAddDTO2LettersContext,
                commentAddDTO4LettersContext, commentAddDTONullContext, commentAddDTOTooLongContext);
    }
}