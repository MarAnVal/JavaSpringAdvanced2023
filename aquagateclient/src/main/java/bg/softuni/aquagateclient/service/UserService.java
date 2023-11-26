package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.data.entity.UserEntity;
import bg.softuni.aquagateclient.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagateclient.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.data.model.UserEditDTO;
import bg.softuni.aquagateclient.data.model.UserRegistrationDTO;
import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

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


    public void edit(UserEditDTO userEditDTO) throws UserNotFoundException, RoleNotFoundException {
        UserEntity userEntity = userRepo.findUserByUsername(userEditDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);
        userEntity.setLevel(LevelEnum.valueOf(userEditDTO.getLevel()));
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.valueOf(userEditDTO.getRole())));
        userRepo.save(userEntity);
    }

    public UserEntity findUserByUsername(String name) throws UserNotFoundException {
        return userRepo.findUserByUsername(name).orElseThrow(UserNotFoundException::new);
    }

    public void register(UserRegistrationDTO userRegistrationDTO) throws RoleNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationDTO.getUsername());
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.USER));
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        userRepo.save(userEntity);
    }

    public void initAdmin() throws RoleNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setEmail("admin@email.exp");
        userEntity.setLevel(LevelEnum.ADVANCED);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.ADMIN));
        userEntity.setPassword(passwordEncoder.encode("admin"));
        userRepo.save(userEntity);
    }

    public void initModerator() throws RoleNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("moderator");
        userEntity.setEmail("moderator@email.exp");
        userEntity.setLevel(LevelEnum.ADVANCED);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.MODERATOR));
        userEntity.setPassword(passwordEncoder.encode("moderator"));
        userRepo.save(userEntity);
    }

}
