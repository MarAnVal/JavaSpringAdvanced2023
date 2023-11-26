package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.data.entity.Role;
import bg.softuni.aquagateclient.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagateclient.repository.RoleRepo;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void init() {
        for (RoleEnum value : RoleEnum.values()) {
            if (roleRepo.findRoleByName(value).isEmpty()) {
                Role role = new Role();
                role.setName(value);
                roleRepo.save(role);
            }
        }
    }

    public List<Role> getRolesByName(RoleEnum roleEnum) throws RoleNotFoundException {
        switch (roleEnum) {
            case USER -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new));
            }
            case MODERATOR -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR).orElseThrow(RoleNotFoundException::new));
            }
            case ADMIN -> {
                return List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.ADMIN).orElseThrow(RoleNotFoundException::new));
            }
            default -> throw new RoleNotFoundException();
        }
    }
}


