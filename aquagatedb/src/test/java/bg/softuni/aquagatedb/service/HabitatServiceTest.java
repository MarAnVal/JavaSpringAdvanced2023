package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.enumeration.HabitatEnum;
import bg.softuni.aquagatedb.repository.HabitatRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HabitatServiceTest {
    private final HabitatRepo habitatRepo;
    private final HabitatService habitatService;
    private final Habitat freshwaterHabitat;

    HabitatServiceTest() {
        habitatRepo = mock(HabitatRepo.class);
        habitatService = new HabitatService(habitatRepo);

        freshwaterHabitat = new Habitat();
        freshwaterHabitat.setName(HabitatEnum.FRESHWATER);
        freshwaterHabitat.setId(1L);
    }

    @Test
    void testFindHabitatByNameSuccessful() throws ObjectNotFoundException {
        // Arrange
        when(habitatRepo.findHabitatByName(HabitatEnum.FRESHWATER)).thenReturn(Optional.ofNullable(freshwaterHabitat));

        // Act
        Habitat habitat = habitatService.findHabitatByName("FRESHWATER");

        // Assert
        assertEquals(freshwaterHabitat, habitat);
    }

    @Test
    void testFindHabitatByNameUnsuccessfulWithExistingEnum(){
        // Arrange
        when(habitatRepo.findHabitatByName(HabitatEnum.FRESHWATER)).thenReturn(Optional.empty());

        // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> habitatService.findHabitatByName("FRESHWATER"));
    }

    @Test
    void testFindHabitatByNameUnsuccessfulWithoutExistingEnum(){
        // Arrange
        when(habitatRepo.findHabitatByName(HabitatEnum.FRESHWATER)).thenReturn(Optional.empty());

        // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> habitatService.findHabitatByName("to test"));
    }

    @Test
    void testInitTrue() {
        // Arrange
        when(habitatRepo.findHabitatByName(HabitatEnum.FRESHWATER)).thenReturn(Optional.empty());

        // Act
        boolean init = habitatService.init();

        // Assert
        assertTrue(init);
    }

    @Test
    void testInitFalse() {
        // Arrange
        when(habitatRepo.findHabitatByName(HabitatEnum.FRESHWATER))
                .thenReturn(Optional.ofNullable(freshwaterHabitat));
        when(habitatRepo.findHabitatByName(HabitatEnum.BLACK_WATER))
                .thenReturn(Optional.ofNullable(freshwaterHabitat));
        when(habitatRepo.findHabitatByName(HabitatEnum.BRACKISH_WATER))
                .thenReturn(Optional.ofNullable(freshwaterHabitat));
        when(habitatRepo.findHabitatByName(HabitatEnum.REEF))
                .thenReturn(Optional.ofNullable(freshwaterHabitat));

        // Act
        boolean init = habitatService.init();

        // Assert
        assertFalse(init);
    }
}