package bg.softuni.aquagateclient.init;

import bg.softuni.aquagateclient.service.RoleService;
import bg.softuni.aquagateclient.service.UserService;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        roleService.init();
        try {
            userService.initAdmin();
            userService.initModerator();

        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
