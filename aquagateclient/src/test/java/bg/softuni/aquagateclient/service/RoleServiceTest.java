package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.RoleRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleServiceTest {
    private final RoleRepo roleRepo;
    private final RoleService roleService;
    private final Role userRole;
    private final Role moderatorRole;
    private final Role adminRole;

    public RoleServiceTest() {
        roleRepo = mock(RoleRepo.class);

        roleService = new RoleService(roleRepo);

        userRole = new Role();
        userRole.setName(RoleEnum.USER);
        userRole.setId(1L);

        moderatorRole = new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        moderatorRole.setId(2L);

        adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        adminRole.setId(2L);
    }

    @Test
    void testInitTrue() {
        // Arrange // Act
        boolean init = roleService.init();

        // Assert
        assertTrue(init);
    }
    @Test
    void testInitFalse() {
        // Arrange
        when(roleRepo.findRoleByName(RoleEnum.USER))
                .thenReturn(userRole);
        when(roleRepo.findRoleByName(RoleEnum.MODERATOR))
                .thenReturn(moderatorRole);
        when(roleRepo.findRoleByName(RoleEnum.ADMIN))
                .thenReturn(adminRole);

        // Act
        boolean init = roleService.init();

        // Assert
        assertFalse(init);
    }

    @Test
    void testGetRolesByNameUser() throws ObjectNotFoundException {
        // Arrange
        when(roleRepo.findRoleByName(RoleEnum.USER))
                .thenReturn(userRole);

        // Act
        List<Role> rolesByName = roleService.getRolesByName(RoleEnum.USER);

        // Assert
        assertEquals(1, rolesByName.size());
        assertEquals(RoleEnum.USER, rolesByName.get(0).getName());
    }

    @Test
    void testGetRolesByNameModerator() throws ObjectNotFoundException {
        // Arrange
        when(roleRepo.findRoleByName(RoleEnum.USER))
                .thenReturn(userRole);
        when(roleRepo.findRoleByName(RoleEnum.MODERATOR))
                .thenReturn(moderatorRole);

        // Act
        List<Role> rolesByName = roleService.getRolesByName(RoleEnum.MODERATOR);

        // Assert
        assertEquals(2, rolesByName.size());
        assertEquals(RoleEnum.USER, rolesByName.get(0).getName());
        assertEquals(RoleEnum.MODERATOR, rolesByName.get(1).getName());
    }

    @Test
    void testGetRolesByNameAdmin() throws ObjectNotFoundException {
        // Arrange
        when(roleRepo.findRoleByName(RoleEnum.USER))
                .thenReturn(userRole);
        when(roleRepo.findRoleByName(RoleEnum.MODERATOR))
                .thenReturn(moderatorRole);
        when(roleRepo.findRoleByName(RoleEnum.ADMIN))
                .thenReturn(adminRole);

        // Act
        List<Role> rolesByName = roleService.getRolesByName(RoleEnum.ADMIN);

        // Assert
        assertEquals(3, rolesByName.size());
        assertEquals(RoleEnum.USER, rolesByName.get(0).getName());
        assertEquals(RoleEnum.MODERATOR, rolesByName.get(1).getName());
        assertEquals(RoleEnum.ADMIN, rolesByName.get(2).getName());
    }
}