package bg.softuni.aquagatedb.init;

import bg.softuni.aquagatedb.service.HabitatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final HabitatService habitatService;

    public DBInit(HabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @Override
    public void run(String... args){
        habitatService.init();
    }
}
