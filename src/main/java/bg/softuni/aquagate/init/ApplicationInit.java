package bg.softuni.aquagate.init;

import bg.softuni.aquagate.service.HabitatService;
import bg.softuni.aquagate.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements CommandLineRunner {
    private final RoleService roleService;
    private final HabitatService habitatService;

    public ApplicationInit(RoleService roleService, HabitatService habitatService) {
        this.roleService = roleService;
        this.habitatService = habitatService;
    }

    @Override
    public void run(String... args){
        //TODO admin init?
        roleService.init();
        habitatService.init();
    }
}
