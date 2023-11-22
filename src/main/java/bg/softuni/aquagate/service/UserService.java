package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.repository.UserRepo;
import bg.softuni.aquagate.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepo userRepo, RoleService roleService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    public void edit(UserEditDTO userEditDTO) throws UserNotFoundException, RoleNotFoundException {
        UserEntity user = userRepo.findUserByUsername(userEditDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);

        List<Role> editedRolesListByName = roleService
                .getEditedRolesListByName(RoleEnum.valueOf(userEditDTO.getRole()));
        user.setRoles(editedRolesListByName);

        userRepo.save(user);
    }

    public UserEntity findUserByUsername(String username) throws UserNotFoundException {
        return userRepo.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
