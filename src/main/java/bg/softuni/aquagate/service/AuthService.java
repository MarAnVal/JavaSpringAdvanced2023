package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import bg.softuni.aquagate.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, RoleService roleService,
                       ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistrationDTO userRegistrationDTO) throws RoleNotFoundException {
        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setLevel(LevelEnum.BEGINNER);
        user.setRoles(roleService.getEditedRolesListByName(RoleEnum.USER));
        userRepo.save(user);
    }
}
