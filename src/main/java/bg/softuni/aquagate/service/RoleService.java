package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void init() {
        for (RoleEnum value : RoleEnum.values()) {
            if (roleRepo.findByName(value).isEmpty()) {
                Role role = new Role();
                role.setName(value);
                roleRepo.save(role);
            }
        }
    }

    public List<Role> getRolesListByName(RoleEnum roleEnum) {
        List<Role> roles = new ArrayList<>();
        switch (roleEnum) {
            case USER:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER));
                break;

            case MODERATOR:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR));
                break;

            case ADMIN:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR),
                        roleRepo.findRoleByName(RoleEnum.ADMIN));
                break;

        }
        return roles;
    }
}
