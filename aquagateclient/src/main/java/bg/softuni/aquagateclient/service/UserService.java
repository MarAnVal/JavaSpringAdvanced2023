package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.UserEditDTO;
import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity editUser(UserEditDTO userEditDTO) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findUserByUsername(userEditDTO.getUsername())
                .orElse(null);
        if (userEntity == null) {
            throw new ObjectNotFoundException("User not found!");
        } else {
            userEntity.setLevel(LevelEnum.valueOf(userEditDTO.getLevel()));
            userEntity.setRoles(new ArrayList<>());
            List<Role> rolesByName = roleService.getRolesByName(RoleEnum.valueOf(userEditDTO.getRole()));
            userRepo.save(userEntity);
            userEntity.getRoles().addAll(rolesByName);
            userRepo.save(userEntity);

            return userEntity;
        }
    }

    public UserEntity findUserByUsername(String name) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findUserByUsername(name).orElse(null);
        if (userEntity == null) {
            throw new ObjectNotFoundException("User not found!");
        }
        return userEntity;
    }

    public UserEntity registerUser(UserRegistrationDTO userRegistrationDTO) throws ObjectNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationDTO.getUsername());
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.USER));
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        userRepo.save(userEntity);

        UserEntity registeredUser = userRepo.findUserByUsername(userEntity.getUsername()).orElse(null);
        if (registeredUser == null) {
            throw new ObjectNotFoundException("There was problem with registration the user. Please try again!");
        }
        userEntity.setId(registeredUser.getId());

        return userEntity;
    }

    public boolean initAdmin() throws ObjectNotFoundException {
        if (userRepo.count() < 1) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setEmail("admin@email.exp");
            userEntity.setLevel(LevelEnum.ADVANCED);
            userEntity.setRoles(roleService.getRolesByName(RoleEnum.ADMIN));
            userEntity.setPassword(passwordEncoder.encode("admin"));
            userRepo.save(userEntity);
            return true;
        }
        return false;
    }

    public boolean initModerator() throws ObjectNotFoundException {
        if (userRepo.count() < 2) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("moderator");
            userEntity.setEmail("moderator@email.exp");
            userEntity.setLevel(LevelEnum.ADVANCED);
            userEntity.setRoles(roleService.getRolesByName(RoleEnum.MODERATOR));
            userEntity.setPassword(passwordEncoder.encode("moderator"));
            userRepo.save(userEntity);
            return true;
        }
        return false;
    }

    public UserEntity getUserById(Long id) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findById(id).orElse(null);
        if (userEntity == null) {
            throw new ObjectNotFoundException("User not found!");
        }
        return userEntity;
    }
}
