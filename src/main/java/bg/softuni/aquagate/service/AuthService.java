package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Level;
import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.data.view.UserProfileView;
import bg.softuni.aquagate.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final LevelService levelService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepo userRepo, RoleService roleService, LevelService levelService, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.levelService = levelService;
        this.modelMapper = modelMapper;
    }

    public void register(UserRegistrationDTO userRegistrationDTO) {
        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);

        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRolesByName(RoleEnum.USER));
        user.setRole(roles);

        Level level = levelService.findLevelByName(LevelEnum.BEGINNER);
        user.setLevel(level);

        userRepo.save(user);
    }

    public UserProfileView findUserByEmail(String email) {
        UserEntity user = userRepo.findUserByEmail(email);
        UserProfileView userProfileView = modelMapper.map(user, UserProfileView.class);
        userProfileView.setLevel(user.getLevel().getName().toString());

        return userProfileView;
    }

    public UserProfileView findUserByUsername(String username) {
        UserEntity user = userRepo.findUserByUsername(username);
        UserProfileView userProfileView = modelMapper.map(user, UserProfileView.class);
        userProfileView.setLevel(user.getLevel().getName().toString());

        return userProfileView;
    }
}
