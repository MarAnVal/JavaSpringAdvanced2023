package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.UserEditDTO;
import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserRepo userRepo;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private Role userRole;
    private Role moderatorRole;

    @BeforeEach
    public void init() {
        this.userRepo = mock(UserRepo.class);

        this.roleService = mock(RoleService.class);

        this.passwordEncoder = mock(Pbkdf2PasswordEncoder.class);

        this.userService = new UserService(userRepo, roleService, passwordEncoder);

        this.userRole = new Role();
        this.userRole.setName(RoleEnum.USER);
        this.userRole.setId(1L);

        this.moderatorRole = new Role();
        this.moderatorRole.setName(RoleEnum.MODERATOR);
        this.moderatorRole.setId(2L);
    }

    @Test
    void testEditUserWhenUserIsNotFound() {
        // Arrange
        UserEditDTO userEntity = new UserEditDTO();
        userEntity.setUsername("testUsername");
        userEntity.setLevel(String.valueOf(LevelEnum.ADVANCED));
        userEntity.setRole("USER");

        //Act //Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.editUser(userEntity));
    }

    @Test
    void testEditUserWhenUserIsFound() throws ObjectNotFoundException {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(List.of(userRole));

        when(userRepo.findUserByUsername("testUsername"))
                .thenReturn(Optional.of(userEntity));

        UserEditDTO user = new UserEditDTO();
        user.setUsername("testUsername");
        user.setLevel(String.valueOf(LevelEnum.ADVANCED));
        user.setRole("MODERATOR");

        when(roleService.getRolesByName(RoleEnum.valueOf("MODERATOR")))
                .thenReturn(List.of(userRole, moderatorRole));

        //Act
        UserEntity editedUser = userService.editUser(user);

        //Assert
        assertEquals(editedUser.getUsername(), user.getUsername());
        assertEquals(editedUser.getLevel().toString(), user.getLevel());
        assertTrue(editedUser.getRoles().contains(moderatorRole));
        assertTrue(editedUser.getRoles().contains(userRole));
    }

    @Test
    void testFindUserByUsernameWhenUserIsNotFound() {
        // Arrange //Act //Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.findUserByUsername("testUsername"));
    }

    @Test
    void testFindUserByUsernameWhenUserIsFound() throws ObjectNotFoundException {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(List.of(userRole));

        when(userRepo.findUserByUsername("testUsername"))
                .thenReturn(Optional.of(userEntity));

        // Act
        UserEntity testUsername = userService.findUserByUsername("testUsername");

        // Assert
        assertEquals(testUsername, userEntity);
    }

    @Test
    void testGetUserByIdWhenUserIsNotFound() {
        // Arrange //Act //Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByIdWhenUserIsFound() throws ObjectNotFoundException {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUsername");
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(List.of(userRole));

        when(userRepo.findById(1L))
                .thenReturn(Optional.of(userEntity));

        // Act
        UserEntity testUsername = userService.getUserById(1L);

        // Assert
        assertEquals(testUsername, userEntity);
    }

    @Test
    void testRegisterUserSuccessfulRegistered() throws ObjectNotFoundException {
        // Arrange
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testUsername");
        userRegistrationDTO.setEmail("test@test");
        userRegistrationDTO.setPassword("testPass");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(roleService.getRolesByName(RoleEnum.USER))
                .thenReturn(List.of(userRole));
        when(passwordEncoder.encode("testPass"))
                .thenReturn("testPass");
        when(userRepo.findUserByUsername("testUsername"))
                .thenReturn(Optional.of(userEntity));


        // Act
        UserEntity savedUser = userService.registerUser(userRegistrationDTO);

        // Assert
        assertEquals(userRegistrationDTO.getUsername(), savedUser.getUsername());
        assertEquals(userRegistrationDTO.getEmail(), savedUser.getEmail());
        assertEquals(userRegistrationDTO.getPassword(), savedUser.getPassword());
        assertEquals(LevelEnum.BEGINNER, savedUser.getLevel());
        assertEquals(1, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().contains(userRole));
        assertEquals(userEntity.getId(), savedUser.getId());
    }

    @Test
    void testRegisterUserUnSuccessfulRegistered() throws ObjectNotFoundException {
        // Arrange
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testUsername");
        userRegistrationDTO.setEmail("test@test");
        userRegistrationDTO.setPassword("testPass");

        when(roleService.getRolesByName(RoleEnum.USER))
                .thenReturn(List.of(userRole));
        when(passwordEncoder.encode("testPass"))
                .thenReturn("testPass");


        // Act // Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.registerUser(userRegistrationDTO));
    }

    @Test
    void testInitAdminTrue() throws ObjectNotFoundException {
        // Arrange 
        when(userRepo.count())
                .thenReturn(0L);
        
        // Act 
        boolean result = userService.initAdmin();
        
        // Assert
        assertTrue(result);
    }

    @Test
    void testInitAdminFalse() throws ObjectNotFoundException {
        // Arrange
        when(userRepo.count())
                .thenReturn(5L);

        // Act
        boolean result = userService.initAdmin();

        // Assert
        assertFalse(result);
    }

    @Test
    void testInitModeratorTrue() throws ObjectNotFoundException {
        // Arrange 
        when(userRepo.count())
                .thenReturn(1L);

        // Act 
        boolean result = userService.initModerator();

        // Assert
        assertTrue(result);
    }

    @Test
    void testInitModeratorFalse() throws ObjectNotFoundException {
        // Arrange
        when(userRepo.count())
                .thenReturn(5L);

        // Act
        boolean result = userService.initModerator();

        // Assert
        assertFalse(result);
    }
}