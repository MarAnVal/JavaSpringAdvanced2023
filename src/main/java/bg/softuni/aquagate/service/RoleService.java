package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
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

    public List<Role> getEditedRolesListByName(RoleEnum roleEnum) throws RoleNotFoundException {
        List<Role> roles;
        switch (roleEnum) {
            case USER:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new));
                break;

            case MODERATOR:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR).orElseThrow(RoleNotFoundException::new));
                break;

            case ADMIN:
                roles = List.of(roleRepo.findRoleByName(RoleEnum.USER).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.MODERATOR).orElseThrow(RoleNotFoundException::new),
                        roleRepo.findRoleByName(RoleEnum.ADMIN).orElseThrow(RoleNotFoundException::new));
                break;
            default:
                throw new RoleNotFoundException();

        }
        return roles;
    }
}
