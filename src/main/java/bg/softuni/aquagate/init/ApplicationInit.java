package bg.softuni.aquagate.init;

import bg.softuni.aquagate.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements CommandLineRunner {
    private final RoleService roleService;

    public ApplicationInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.init();
    }
}
