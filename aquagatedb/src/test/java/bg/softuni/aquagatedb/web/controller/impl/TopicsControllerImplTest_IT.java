package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.repository.HabitatRepo;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.service.HabitatService;
import bg.softuni.aquagatedb.service.PictureService;
import bg.softuni.aquagatedb.service.TopicService;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TopicsControllerImplTest_IT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TopicService topicService;
    @Autowired
    private PictureRepo pictureRepo;
    @Autowired
    private HabitatService habitatService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private HabitatRepo habitatRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() throws ObjectNotFoundException {
        topicRepo.deleteAll();
        pictureRepo.deleteAll();
        commentRepo.deleteAll();

        habitatService.init();

        Topic topic1 = new Topic();
        topic1.setComments(new ArrayList<>());
        topic1.setDate(LocalDate.now(ZoneOffset.UTC));
        topic1.setApproved(false);
        topic1.setName("To do test");
        Habitat habitat1 = habitatService.findHabitatByName("FRESHWATER");
        topic1.setHabitat(habitat1);
        topic1.setLevel(LevelEnum.BEGINNER);
        topic1.setDescription("to do test topic");
        topic1.setAuthor(2L);
        topic1.setVideoUrl("testVideoUrl");
        Picture picture1 = pictureService.addPicture("testPictureUrl.bnp");
        topic1.setPicture(picture1);
        topic1.setComments(new ArrayList<>());

        Topic topic2 = new Topic();
        topic2.setComments(new ArrayList<>());
        topic2.setDate(LocalDate.now(ZoneOffset.UTC));
        topic2.setApproved(true);
        topic2.setName("To do test");
        Habitat habitat2 = habitatService.findHabitatByName("FRESHWATER");
        topic2.setHabitat(habitat2);
        topic2.setLevel(LevelEnum.BEGINNER);
        topic2.setDescription("to do test topic");
        topic2.setAuthor(2L);
        topic2.setVideoUrl("testVideoUrl");
        Picture picture2 = pictureService.addPicture("testPictureUrl.jped");
        topic2.setPicture(picture2);
        topic2.setComments(new ArrayList<>());

        topicRepo.save(topic1);
        topicRepo.save(topic2);
    }

    @Test
    void testGetAllTopicsWithoutTopics200Response() throws Exception {
        // Arrange // Act
        List<TopicView> allTopics = topicService.findAllTopics();

        // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/all")
        ).andExpectAll(status().is2xxSuccessful());

        assertEquals(2, allTopics.size());
    }

    @Test
    void testGetAllTopicsWithTopics200Response() throws Exception {
        // Arrange
        topicRepo.deleteAll();

        // Act
        List<TopicView> allTopics = topicService.findAllTopics();

        // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/all")
        ).andExpect(status().is2xxSuccessful());

        assertEquals(0, allTopics.size());
    }

    @Test
    void testGetTopicDetails200Response() throws Exception {
        // Arrange
        assertEquals(2, topicRepo.count());
        List<Topic> all = topicRepo.findAll();
        Long id = all.get(0).getId();

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/details/" + id)
        ).andExpect(status().is2xxSuccessful());

        // Arrange
        id = all.get(1).getId();

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/topics/details/" + id)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testDoRemove200Response() throws Exception {
        // Arrange
        assertEquals(2, topicRepo.count());
        List<Topic> all = topicRepo.findAll();
        Long id = all.get(0).getId();

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/topics/remove/" + id)
        ).andExpect(status().is2xxSuccessful());
        assertEquals(1, topicRepo.count());

        // Arrange
        all = topicRepo.findAll();
        id = all.get(0).getId();

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/topics/remove/" + id)
        ).andExpect(status().is2xxSuccessful());
        assertEquals(0, topicRepo.count());
    }

    @Test
    void testDoApprove200Response() throws Exception {
        // Arrange // Act // Assert
        assertEquals(2, topicRepo.count());
        List<Topic> all = topicRepo.findAll();
        Long id;
        if (all.get(0).getApproved()) {
            id = all.get(1).getId();
        } else {
            id = all.get(0).getId();
        }

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/topics/approve/" + id)
        ).andExpect(status().is2xxSuccessful());
        assertEquals(2, topicRepo.count());
        assertTrue(topicRepo.findById(id).isPresent());
        assertTrue(topicRepo.findById(id).get().getApproved());
    }

    @Test
    void testDoTopicAdd200Response() throws Exception {
        // Arrange
        topicRepo.deleteAll();

        TopicAddDTO topicAddDTO200 = new TopicAddDTO();
        topicAddDTO200.setName("testName");
        topicAddDTO200.setHabitat("FRESHWATER");
        topicAddDTO200.setLevel("BEGINNER");
        topicAddDTO200.setDescription("testDescription");
        topicAddDTO200.setPictureUrl("testPictureUrl.jpg");
        topicAddDTO200.setVideoUrl("12345678901");
        topicAddDTO200.setAuthor(1L);
        String json = objectMapper.writeValueAsString(topicAddDTO200);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/topics/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is2xxSuccessful());
        List<Topic> all = topicRepo.findAll();

        assertEquals(1, all.size());
        Topic topic = all.get(0);
        assertEquals(topicAddDTO200.getName(), topic.getName());
        assertEquals(topicAddDTO200.getHabitat(), topic.getHabitat().getName().toString());
        assertEquals(topicAddDTO200.getLevel(), topic.getLevel().toString());
        assertEquals(topicAddDTO200.getDescription(), topic.getDescription());
        assertEquals(topicAddDTO200.getPictureUrl(), topic.getPicture().getPictureUrl());
        assertEquals(topicAddDTO200.getVideoUrl(), topic.getVideoUrl());
        assertEquals(topicAddDTO200.getAuthor(), topic.getAuthor());
    }

    @ParameterizedTest
    @MethodSource
    void testDoTopicAdd422ResponseInvalidTopicDTO(TopicAddDTO topicAddDTO) throws Exception {
        // Arrange
        long count = topicRepo.count();

        String json = objectMapper.writeValueAsString(topicAddDTO);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/topics/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is4xxClientError());

        assertEquals(count, topicRepo.count());
    }

    static Stream<TopicAddDTO> testDoTopicAdd422ResponseInvalidTopicDTO() {
        TopicAddDTO topicAddDTOTooShortName = new TopicAddDTO();
        topicAddDTOTooShortName.setName("test");
        topicAddDTOTooShortName.setHabitat("FRESHWATER");
        topicAddDTOTooShortName.setLevel("BEGINNER");
        topicAddDTOTooShortName.setDescription("testDescription");
        topicAddDTOTooShortName.setPictureUrl("testPictureUrl.jpg");
        topicAddDTOTooShortName.setVideoUrl("12345678901");
        topicAddDTOTooShortName.setAuthor(1L);

        TopicAddDTO topicAddDTOTooLongName = new TopicAddDTO();
        topicAddDTOTooLongName.setName("testTooLongNameMoreThan20Characters");
        topicAddDTOTooLongName.setHabitat("FRESHWATER");
        topicAddDTOTooLongName.setLevel("BEGINNER");
        topicAddDTOTooLongName.setDescription("testDescription");
        topicAddDTOTooLongName.setPictureUrl("testPictureUrl.jpg");
        topicAddDTOTooLongName.setVideoUrl("12345678901");
        topicAddDTOTooLongName.setAuthor(1L);

        TopicAddDTO topicAddDTONullName = new TopicAddDTO();
        topicAddDTONullName.setName(null);
        topicAddDTONullName.setHabitat("FRESHWATER");
        topicAddDTONullName.setLevel("BEGINNER");
        topicAddDTONullName.setDescription("testDescription");
        topicAddDTONullName.setPictureUrl("testPictureUrl.jpg");
        topicAddDTONullName.setVideoUrl("12345678901");
        topicAddDTONullName.setAuthor(1L);

        TopicAddDTO topicAddDTONullHabitat = new TopicAddDTO();
        topicAddDTONullHabitat.setName("testName");
        topicAddDTONullHabitat.setHabitat(null);
        topicAddDTONullHabitat.setLevel("BEGINNER");
        topicAddDTONullHabitat.setDescription("testDescription");
        topicAddDTONullHabitat.setPictureUrl("testPictureUrl.jpg");
        topicAddDTONullHabitat.setVideoUrl("12345678901");
        topicAddDTONullHabitat.setAuthor(1L);

        TopicAddDTO topicAddDTONullLevel = new TopicAddDTO();
        topicAddDTONullLevel.setName("testName");
        topicAddDTONullLevel.setHabitat("FRESHWATER");
        topicAddDTONullLevel.setLevel(null);
        topicAddDTONullLevel.setDescription("testDescription");
        topicAddDTONullLevel.setPictureUrl("testPictureUrl.jpg");
        topicAddDTONullLevel.setVideoUrl("12345678901");
        topicAddDTONullLevel.setAuthor(1L);

        TopicAddDTO topicAddDTOTooShortDescription = new TopicAddDTO();
        topicAddDTOTooShortDescription.setName("testName");
        topicAddDTOTooShortDescription.setHabitat("FRESHWATER");
        topicAddDTOTooShortDescription.setLevel("BEGINNER");
        topicAddDTOTooShortDescription.setDescription("test");
        topicAddDTOTooShortDescription.setPictureUrl("testPictureUrl.jpg");
        topicAddDTOTooShortDescription.setVideoUrl("12345678901");
        topicAddDTOTooShortDescription.setAuthor(1L);

        TopicAddDTO topicAddDTONullDescription = new TopicAddDTO();
        topicAddDTONullDescription.setName("testName");
        topicAddDTONullDescription.setHabitat("FRESHWATER");
        topicAddDTONullDescription.setLevel("BEGINNER");
        topicAddDTONullDescription.setDescription(null);
        topicAddDTONullDescription.setPictureUrl("testPictureUrl.jpg");
        topicAddDTONullDescription.setVideoUrl("12345678901");
        topicAddDTONullDescription.setAuthor(1L);

        TopicAddDTO topicAddDTONoMatchPictureUrl = new TopicAddDTO();
        topicAddDTONoMatchPictureUrl.setName("testName");
        topicAddDTONoMatchPictureUrl.setHabitat("FRESHWATER");
        topicAddDTONoMatchPictureUrl.setLevel("BEGINNER");
        topicAddDTONoMatchPictureUrl.setDescription("testDescription");
        topicAddDTONoMatchPictureUrl.setPictureUrl("testPictureUrl");
        topicAddDTONoMatchPictureUrl.setVideoUrl("12345678901");
        topicAddDTONoMatchPictureUrl.setAuthor(1L);

        TopicAddDTO topicAddDTOTooShortVideoUrl = new TopicAddDTO();
        topicAddDTOTooShortVideoUrl.setName("testName");
        topicAddDTOTooShortVideoUrl.setHabitat("FRESHWATER");
        topicAddDTOTooShortVideoUrl.setLevel("BEGINNER");
        topicAddDTOTooShortVideoUrl.setDescription("testDescription");
        topicAddDTOTooShortVideoUrl.setPictureUrl("testPictureUrl.jpg");
        topicAddDTOTooShortVideoUrl.setVideoUrl("1234567890");
        topicAddDTOTooShortVideoUrl.setAuthor(1L);

        TopicAddDTO topicAddDTOTooLongVideoUrl = new TopicAddDTO();
        topicAddDTOTooLongVideoUrl.setName("testName");
        topicAddDTOTooLongVideoUrl.setHabitat("FRESHWATER");
        topicAddDTOTooLongVideoUrl.setLevel("BEGINNER");
        topicAddDTOTooLongVideoUrl.setDescription("testDescription");
        topicAddDTOTooLongVideoUrl.setPictureUrl("testPictureUrl.jpg");
        topicAddDTOTooLongVideoUrl.setVideoUrl("123456789012");
        topicAddDTOTooLongVideoUrl.setAuthor(1L);

        TopicAddDTO topicAddDTONullAuthor = new TopicAddDTO();
        topicAddDTONullAuthor.setName("testName");
        topicAddDTONullAuthor.setHabitat("FRESHWATER");
        topicAddDTONullAuthor.setLevel("BEGINNER");
        topicAddDTONullAuthor.setDescription("testDescription");
        topicAddDTONullAuthor.setPictureUrl("testPictureUrl.jpg");
        topicAddDTONullAuthor.setVideoUrl("12345678901");
        topicAddDTONullAuthor.setAuthor(null);

        return Stream.of(topicAddDTOTooShortName, topicAddDTOTooLongName, topicAddDTONullName,
                topicAddDTONullHabitat,
                topicAddDTONullLevel,
                topicAddDTOTooShortDescription, topicAddDTONullDescription,
                topicAddDTONoMatchPictureUrl,
                topicAddDTOTooShortVideoUrl, topicAddDTOTooLongVideoUrl,
                topicAddDTONullAuthor);
    }

    @Test
    void testDoTopicAdd500ResponseNoHabitatFound() throws Exception, ObjectNotFoundException {
        // Arrange
        habitatRepo.delete(habitatService.findHabitatByName("REEF"));
        long count = topicRepo.count();

        TopicAddDTO topicAddDTO = new TopicAddDTO();
        topicAddDTO.setName("testName");
        topicAddDTO.setHabitat("REEF");
        topicAddDTO.setLevel("BEGINNER");
        topicAddDTO.setDescription("testDescription");
        topicAddDTO.setPictureUrl("testPictureUrl.jpg");
        topicAddDTO.setVideoUrl("12345678901");
        topicAddDTO.setAuthor(1L);
        String json = objectMapper.writeValueAsString(topicAddDTO);

        // Act // Assert
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/topics/add")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(status().is5xxServerError());

        assertEquals(count, topicRepo.count());
    }
}