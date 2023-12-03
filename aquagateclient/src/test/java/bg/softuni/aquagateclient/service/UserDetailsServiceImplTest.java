package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private UserService userService;
    private UserDetailsService userDetailsService;
    private Role userRole;
    private Role moderatorRole;

    @BeforeEach
    public void init() {

        this.userService = mock(UserService.class);

        this.userDetailsService = new UserDetailsServiceImpl(userService);

        this.userRole = new Role();
        this.userRole.setName(RoleEnum.USER);
        this.userRole.setId(1L);

        this.moderatorRole = new Role();
        this.moderatorRole.setName(RoleEnum.MODERATOR);
        this.moderatorRole.setId(2L);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() throws ObjectNotFoundException {
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
}