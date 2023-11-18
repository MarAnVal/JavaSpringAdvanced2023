package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepo userRepo, RoleService roleService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    public void edit(UserEditDTO userEditDTO) {
        UserEntity user = userRepo.findUserByUsername(userEditDTO.getUsername());
        user.setRoles(roleService.getRolesListByName(RoleEnum.valueOf(userEditDTO.getRole())));

        userRepo.save(user);
    }

    public UserEntity findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }
}
