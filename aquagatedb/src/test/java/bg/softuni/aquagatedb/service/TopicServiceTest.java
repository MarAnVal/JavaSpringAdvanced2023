package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.TopicDetailsView;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import bg.softuni.aquagatedb.model.entity.Comment;
import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.model.entity.Topic;
import bg.softuni.aquagatedb.model.entity.enumeration.HabitatEnum;
import bg.softuni.aquagatedb.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagatedb.repository.TopicRepo;
import bg.softuni.aquagatedb.service.util.DateProvider;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {
    private final TopicRepo topicRepo;
    private final TopicService topicService;

    TopicServiceTest() {
        topicRepo = mock(TopicRepo.class);
        PictureService pictureService = mock(PictureService.class);
        HabitatService habitatService = mock(HabitatService.class);
        ModelMapper modelMapper = new ModelMapper();
        DateProvider dateProvider = mock(DateProvider.class);
        topicService = new TopicService(topicRepo,habitatService,pictureService, modelMapper, dateProvider);
    }

    @Test
    void testAddTopicMappingTopicView() throws ObjectNotFoundException {
        // Arrange
        TopicAddDTO topicAddDTO = new TopicAddDTO();
        topicAddDTO.setAuthor(5L);
        topicAddDTO.setName("test");
        topicAddDTO.setPictureUrl("testPictureUrl");
        topicAddDTO.setVideoUrl(null);
        topicAddDTO.setHabitat("FRESHWATER");
        topicAddDTO.setDescription("to test");
        topicAddDTO.setLevel("BEGINNER");

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setAuthor(5L);
        topic.setName("test");
        Picture picture = new Picture();
        picture.setPictureUrl("testPictureUrl");
        topic.setPicture(picture);
        topic.setVideoUrl(null);
        Habitat habitat = new Habitat();
        habitat.setName(HabitatEnum.FRESHWATER);
        topic.setHabitat(habitat);
        topic.setDescription("to test");
        topic.setLevel(LevelEnum.BEGINNER);
        topic.setComments(new ArrayList<>());

        when(topicRepo.findAll()).thenReturn(List.of(topic));

        // Act
        TopicView topicView = topicService.addTopic(topicAddDTO);

        // Assert
        Assertions.assertEquals(topic.getId(), topicView.getId());
        Assertions.assertEquals(topic.getAuthor(), topicView.getAuthor());
        Assertions.assertEquals(topic.getApproved(), topicView.getApproved());
        Assertions.assertEquals(topic.getPicture().getPictureUrl(), topicView.getPictureUrl());
        Assertions.assertEquals(topic.getName(), topicView.getName());
        Assertions.assertEquals(topic.getDescription(), topicView.getDescription());
        Assertions.assertEquals(topic.getComments().size(), topicView.getCommentCount());
    }

    @Test
    void testApproveTopicSuccessfulMapTopicDetailView() throws ObjectNotFoundException {
        // Arrange
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setAuthor(5L);
        topic.setName("test");
        Picture picture = new Picture();
        picture.setPictureUrl("testPictureUrl");
        topic.setPicture(picture);
        topic.setVideoUrl(null);
        Habitat habitat = new Habitat();
        habitat.setName(HabitatEnum.FRESHWATER);
        topic.setHabitat(habitat);
        topic.setDescription("to test");
        topic.setLevel(LevelEnum.BEGINNER);

        Comment comment = new Comment();
        comment.setId(5L);
        comment.setTopic(topic);
        comment.setContext("todo test");
        comment.setAuthorId(10L);
        topic.setComments(List.of(comment));

        when(topicRepo.findById(1L)).thenReturn(Optional.of(topic));

        // Act
        TopicDetailsView topicDetailsView = topicService.approveTopic(1L);

        // Assert
        assertEquals(topic.getId(), topicDetailsView.getId());
        assertEquals(topic.getAuthor(), topicDetailsView.getAuthor());
        assertEquals(topic.getApproved(), topicDetailsView.getApproved());
        assertEquals(topic.getName(), topicDetailsView.getName());
        assertEquals(topic.getDescription(), topicDetailsView.getDescription());
        assertEquals(topic.getLevel().toString(), topicDetailsView.getLevel());
        assertEquals(topic.getPicture().getPictureUrl(), topicDetailsView.getPicture());
        assertEquals(topic.getHabitat().getName().toString(), topicDetailsView.getHabitat());
        assertEquals(topic.getVideoUrl(), topicDetailsView.getVideoUrl());
        assertEquals(topic.getComments().size(), topicDetailsView.getComments().size());
        assertEquals(topic.getComments().get(0).getTopic().getId(), topicDetailsView.getComments().get(0).getTopicId());
        assertEquals(topic.getComments().get(0).getAuthorId(), topicDetailsView.getComments().get(0).getAuthorId());
        assertEquals(topic.getComments().get(0).getContext(), topicDetailsView.getComments().get(0).getContext());
        assertEquals(topic.getComments().get(0).getId(), topicDetailsView.getComments().get(0).getId());
    }

    @Test
    void testApproveTopicUnsuccessful(){
        // Arrange // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> topicService.approveTopic(1L));
    }

    @Test
    void testRemoveTopicSuccessful() throws ObjectNotFoundException {
        // Arrange
        when(topicRepo.findById(1L)).thenReturn(Optional.of(new Topic()));

        // Act
        boolean result = topicService.removeTopic(1L);

        // Assert
        assertTrue(result);
    }

    @Test
    void testRemoveTopicUnsuccessful() {
        // Arrange // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> topicService.removeTopic(1L));
    }

    @Test
    void testFindAllTopicsWithTopics() {
        // Arrange
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setAuthor(5L);
        topic.setName("test");
        Picture picture = new Picture();
        picture.setPictureUrl("testPictureUrl");
        topic.setPicture(picture);
        topic.setVideoUrl(null);
        Habitat habitat = new Habitat();
        habitat.setName(HabitatEnum.FRESHWATER);
        topic.setHabitat(habitat);
        topic.setDescription("to test");
        topic.setLevel(LevelEnum.BEGINNER);

        Comment comment = new Comment();
        comment.setId(5L);
        comment.setTopic(topic);
        comment.setContext("todo test");
        comment.setAuthorId(10L);
        topic.setComments(List.of(comment));

        when(topicRepo.findAll()).thenReturn(List.of(topic));

        // Act
        List<TopicView> allTopics = topicService.findAllTopics();

        // Assert
        assertEquals(1, allTopics.size());
        assertEquals(topic.getId(), allTopics.get(0).getId());
        assertEquals(topic.getAuthor(), allTopics.get(0).getAuthor());
        assertEquals(topic.getApproved(), allTopics.get(0).getApproved());
        assertEquals(topic.getName(), allTopics.get(0).getName());
        assertEquals(topic.getDescription(), allTopics.get(0).getDescription());
        assertEquals(topic.getPicture().getPictureUrl(), allTopics.get(0).getPictureUrl());
        assertEquals(topic.getComments().size(), allTopics.get(0).getCommentCount());
    }


    @Test
    void testFindAllTopicsWithoutTopics() {
        // Arrange // Act
        List<TopicView> allTopics = topicService.findAllTopics();

        // Assert
        assertEquals(0, allTopics.size());
    }

    @Test
    void testFindTopicByIdSuccessfulMapTopicDetailView() throws ObjectNotFoundException {
        // Arrange
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setAuthor(5L);
        topic.setName("test");
        Picture picture = new Picture();
        picture.setPictureUrl("testPictureUrl");
        topic.setPicture(picture);
        topic.setVideoUrl(null);
        Habitat habitat = new Habitat();
        habitat.setName(HabitatEnum.FRESHWATER);
        topic.setHabitat(habitat);
        topic.setDescription("to test");
        topic.setLevel(LevelEnum.BEGINNER);

        Comment comment = new Comment();
        comment.setId(5L);
        comment.setTopic(topic);
        comment.setContext("todo test");
        comment.setAuthorId(10L);
        topic.setComments(List.of(comment));

        when(topicRepo.findById(1L)).thenReturn(Optional.of(topic));

        // Act
        TopicDetailsView topicDetailsView = topicService.findTopicById(1L);

        // Assert
        assertEquals(topic.getId(), topicDetailsView.getId());
        assertEquals(topic.getAuthor(), topicDetailsView.getAuthor());
        assertEquals(topic.getApproved(), topicDetailsView.getApproved());
        assertEquals(topic.getName(), topicDetailsView.getName());
        assertEquals(topic.getDescription(), topicDetailsView.getDescription());
        assertEquals(topic.getLevel().toString(), topicDetailsView.getLevel());
        assertEquals(topic.getPicture().getPictureUrl(), topicDetailsView.getPicture());
        assertEquals(topic.getHabitat().getName().toString(), topicDetailsView.getHabitat());
        assertEquals(topic.getVideoUrl(), topicDetailsView.getVideoUrl());
        assertEquals(topic.getComments().size(), topicDetailsView.getComments().size());
        assertEquals(topic.getComments().get(0).getTopic().getId(), topicDetailsView.getComments().get(0).getTopicId());
        assertEquals(topic.getComments().get(0).getAuthorId(), topicDetailsView.getComments().get(0).getAuthorId());
        assertEquals(topic.getComments().get(0).getContext(), topicDetailsView.getComments().get(0).getContext());
        assertEquals(topic.getComments().get(0).getId(), topicDetailsView.getComments().get(0).getId());
    }


    @Test
    void testFindTopicByIdUnsuccessful(){
        // Arrange // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> topicService.findTopicById(1L));
    }

    @Test
    void testFindTopicByIdToAddCommentSuccessful() throws ObjectNotFoundException {
        // Arrange
        Topic topic = new Topic();
        when(topicRepo.findById(1L)).thenReturn(Optional.of(topic));

        // Act
        Topic topicByIdToAddComment = topicService.findTopicByIdToAddComment(1L);

        // Assert
        assertEquals(topic, topicByIdToAddComment);
    }


    @Test
    void testFindTopicByIdToAddCommentUnsuccessful() {
        // Arrange // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> topicService.findTopicByIdToAddComment(1L));
    }
}