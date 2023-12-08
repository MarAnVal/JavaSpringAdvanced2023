package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagatedb.repository.CommentRepo;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.service.HabitatService;
import bg.softuni.aquagatedb.service.PictureService;
import bg.softuni.aquagatedb.service.TopicService;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        ).andExpectAll(status().is2xxSuccessful());

        assertEquals(0, allTopics.size());
    }

    @Test
    void testGetTopicDetails200Response() {

    }

    //TODO refactor after finish it
    @Test
    void testGetTopicDetails404ExceptionHandlerResponse() {
    }

    @Test
    void testDoRemove200Response() {
    }

    @Test
    void testDoRemove500Response() {
    }

    //TODO refactor after finish it
    @Test
    void testDoRemove404ExceptionHandlerResponse() {
    }

    @Test
    void testDoApprove200Response() {
    }

    //TODO refactor after finish it
    @Test
    void testDoApprove404ExceptionHandlerResponse() {
    }

    @Test
    void testDoTopicAdd200Response() {
    }

    @Test
    void testDoTopicAdd422Response() {
    }

    //TODO refactor after finish it
    @Test
    void testDoTopicAdd404ExceptionHandlerResponse() {
    }
}