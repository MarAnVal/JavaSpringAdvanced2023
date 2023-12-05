package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PictureServiceTest {
    private final PictureRepo pictureRepo;
    private final PictureService pictureService;

    PictureServiceTest() {
        pictureRepo = mock(PictureRepo.class);
        pictureService = new PictureService(pictureRepo);
    }

    @Test
    void testAddPictureBlankPictureUrl() throws ObjectNotFoundException {
        // Arrange
        String pictureUrl = "";
        Picture picture = new Picture();
        picture.setPictureUrl("/images/picture-not-found.jpg");
        picture.setId(1L);

        when(pictureRepo.findAll()).thenReturn(List.of(picture));

        // Act
        Picture addedPicture = pictureService.addPicture(pictureUrl);

        // Assert
        assertEquals(picture, addedPicture);
    }

    @Test
    void testAddPictureNullPictureUrl() throws ObjectNotFoundException {
        // Arrange
        Picture picture = new Picture();
        picture.setPictureUrl("/images/picture-not-found.jpg");
        picture.setId(1L);

        when(pictureRepo.findAll()).thenReturn(List.of(picture));

        // Act
        Picture addedPicture = pictureService.addPicture(null);

        // Assert
        assertEquals(picture, addedPicture);
    }

    @Test
    void testAddPictureWithPictureUrl() throws ObjectNotFoundException {
        // Arrange
        String pictureUrl = "to do test";
        Picture picture1 = new Picture();
        picture1.setPictureUrl("to do test");
        picture1.setId(10L);
        Picture picture2 = new Picture();
        picture2.setPictureUrl("to do test");
        picture2.setId(9L);
        Picture picture3 = new Picture();
        picture3.setPictureUrl("to do test");
        picture3.setId(8L);

        when(pictureRepo.findAll()).thenReturn(List.of(picture1, picture2, picture3));

        // Act
        Picture addedPicture = pictureService.addPicture(pictureUrl);

        // Assert
        assertEquals(picture1, addedPicture);
    }

    @Test
    void testAddPictureFailedSave() {
        // Arrange // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> pictureService.addPicture("to do test"));
    }
}