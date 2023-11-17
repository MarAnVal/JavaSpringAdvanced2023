package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
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
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepo userRepo, RoleService roleService, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public void register(UserRegistrationDTO userRegistrationDTO) {
        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);

        user.setRoles(getRegistrationRoles());

        user.setLevel(LevelEnum.BEGINNER);

        userRepo.save(user);
    }

    private List<Role> getRegistrationRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findRoleByName(RoleEnum.USER));
        return roles;
    }

    public UserEntity findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public UserEntity findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    public void edit(UserEditDTO userEditDTO) {
        UserEntity user = userRepo.findUserByUsername(userEditDTO.getUsername());
        LevelEnum level = LevelEnum.valueOf(userEditDTO.getLevel());
        List<Role> roles = getEditedRoles(userEditDTO.getRole(), user.getRoles());

        user.setRoles(roles);
        user.setLevel(level);

        userRepo.save(user);
    }

    private List<Role> getEditedRoles(String role, List<Role> userRoles) {
        //TODO try to find general way to get roles without specific if for any of them
        Role newRole = roleService.findRoleByName(RoleEnum.valueOf(role));
        if (!userRoles.contains(newRole)) {
            userRoles.add(newRole);
            return userRoles;

        } else if (newRole.getName().equals(RoleEnum.USER) && userRoles.size() > 1) {
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(newRole);
            return newRoles;

        } else {
            return userRoles;
        }
    }
}
