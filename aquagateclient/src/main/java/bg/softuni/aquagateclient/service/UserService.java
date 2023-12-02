package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.dto.binding.UserEditDTO;
import bg.softuni.aquagateclient.model.dto.binding.UserRegistrationDTO;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.model.entity.enumeration.LevelEnum;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.UserRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void editUser(UserEditDTO userEditDTO) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findUserByUsername(userEditDTO.getUsername())
                .orElse(null);
        if(userEntity==null){
            throw new ObjectNotFoundException("User not found!");
        }
        userEntity.setLevel(LevelEnum.valueOf(userEditDTO.getLevel()));
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.valueOf(userEditDTO.getRole())));
        userRepo.save(userEntity);
    }

    public UserEntity findUserByUsername(String name) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findUserByUsername(name).orElse(null);
        if(userEntity == null){
            throw new ObjectNotFoundException("User not found!");
        }
        return userEntity;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO) throws ObjectNotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegistrationDTO.getUsername());
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setLevel(LevelEnum.BEGINNER);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.USER));
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        userRepo.save(userEntity);
    }

    public void initAdmin() throws ObjectNotFoundException {
        if (userRepo.findUserByUsername("admin").isEmpty() &&
                userRepo.findUserByEmail("admin@email.exp").isEmpty()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setEmail("admin@email.exp");
            userEntity.setLevel(LevelEnum.ADVANCED);
            userEntity.setRoles(roleService.getRolesByName(RoleEnum.ADMIN));
            userEntity.setPassword(passwordEncoder.encode("admin"));
            userRepo.save(userEntity);
        }
    }

    public void initModerator() throws ObjectNotFoundException {
        if (userRepo.findUserByUsername("moderator").isEmpty() &&
                userRepo.findUserByEmail("moderator@email.exp").isEmpty()) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("moderator");
        userEntity.setEmail("moderator@email.exp");
        userEntity.setLevel(LevelEnum.ADVANCED);
        userEntity.setRoles(roleService.getRolesByName(RoleEnum.MODERATOR));
        userEntity.setPassword(passwordEncoder.encode("moderator"));
        userRepo.save(userEntity);}
    }

    public UserEntity getUserById(Long id) throws ObjectNotFoundException {
        UserEntity userEntity = userRepo.findById(id).orElse(null);
        if(userEntity == null){
            throw new ObjectNotFoundException("User not found!");
        }
        return userEntity;
    }
}
