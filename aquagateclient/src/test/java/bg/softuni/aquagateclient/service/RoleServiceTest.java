package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.RoleRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleServiceTest {
    private RoleRepo roleRepo;
    private RoleService roleService;
    private Role userRole;
    private Role moderatorRole;
    private Role adminRole;

    @BeforeEach
    public void init() {
        this.roleRepo = mock(RoleRepo.class);

        this.roleService = new RoleService(roleRepo);

        this.userRole = new Role();
        this.userRole.setName(RoleEnum.USER);
        this.userRole.setId(1L);

        this.moderatorRole = new Role();
        this.moderatorRole.setName(RoleEnum.MODERATOR);
        this.moderatorRole.setId(2L);

        this.adminRole = new Role();
        this.adminRole.setName(RoleEnum.ADMIN);
        this.adminRole.setId(2L);
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