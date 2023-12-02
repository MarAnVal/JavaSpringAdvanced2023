package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.RoleRepo;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void init() {
        for (RoleEnum value : RoleEnum.values()) {
            if (roleRepo.findRoleByName(value) == null) {
                Role role = new Role();
                role.setName(value);
                roleRepo.save(role);
            }
        }
    }

    public List<Role> getRolesByName(RoleEnum roleEnum) throws ObjectNotFoundException {
        switch (roleEnum) {
            case USER -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER));
            }
            case MODERATOR -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR));
            }
            case ADMIN -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR),
                        roleRepo.findRoleByName(RoleEnum.ADMIN));
            }
            default -> throw new ObjectNotFoundException("Role not found!");
        }
    }
}


