package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final Role userRole;
    private final Role moderatorRole;

    UserDetailsServiceImplTest() {
        userService = mock(UserService.class);

        userDetailsService = new UserDetailsServiceImpl(userService);

        userRole = new Role();
        userRole.setName(RoleEnum.USER);
        userRole.setId(1L);

        moderatorRole = new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        moderatorRole.setId(2L);
    }


    @Test
    void testLoadUserByUsernameUserFound() throws ObjectNotFoundException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUsername("testUsername");
        user.setPassword("testPass");
        user.setRoles(List.of(userRole, moderatorRole));

        when(userService.findUserByUsername("testUsername"))
                .thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUsername");

        // Assert
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameUserNotFound() throws ObjectNotFoundException {
        // Arrange
        when(userService.findUserByUsername("testUsername"))
                .thenThrow(new ObjectNotFoundException("Not Found!"));
        // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> userDetailsService.loadUserByUsername("testUsername"));
    }
}