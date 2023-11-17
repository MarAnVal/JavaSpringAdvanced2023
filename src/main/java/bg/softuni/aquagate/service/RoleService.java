package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public void init(){
        for (RoleEnum value : RoleEnum.values()) {
            if(roleRepo.findByName(value).isEmpty()){
                Role role = new Role();
                role.setName(value);
                roleRepo.save(role);
            }
        }
    }

    public Role findRoleByName(RoleEnum roleEnum) {
        return roleRepo.findRoleByName(roleEnum);
    }

}
