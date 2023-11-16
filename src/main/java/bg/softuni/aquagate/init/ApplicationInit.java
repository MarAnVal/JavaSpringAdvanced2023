package bg.softuni.aquagate.init;

import bg.softuni.aquagate.service.HabitatService;
import bg.softuni.aquagate.service.LevelService;
import bg.softuni.aquagate.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInit implements CommandLineRunner {
    private final HabitatService habitatService;
    private final LevelService levelService;
    private final RoleService roleService;

    public ApplicationInit(HabitatService habitatService, LevelService levelService, RoleService roleService) {
        this.habitatService = habitatService;
        this.levelService = levelService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        habitatService.init();
        levelService.init();
        roleService.init();
    }
}
