package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.entity.enumeration.HabitatEnum;
import bg.softuni.aquagate.repository.HabitatRepo;
import bg.softuni.aquagate.web.error.HabitatNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitatService {
    private final HabitatRepo habitatRepo;

    @Autowired
    public HabitatService(HabitatRepo habitatRepo) {
        this.habitatRepo = habitatRepo;
    }

    public Habitat findHabitatByName(String habitat) throws HabitatNotFoundException {
        return habitatRepo.findByName(HabitatEnum.valueOf(habitat)).orElseThrow(HabitatNotFoundException::new);
    }

    public void init() {
        habitatRepo.deleteAll();

        if (habitatRepo.findByName(HabitatEnum.FRESHWATER).isEmpty()) {
            Habitat habitat = new Habitat();
            habitat.setName(HabitatEnum.FRESHWATER);
            habitat.setThFragmentName("freshwater");
            habitatRepo.save(habitat);
        }

        if (habitatRepo.findByName(HabitatEnum.BLACK_WATER).isEmpty()) {
            Habitat habitat = new Habitat();
            habitat.setName(HabitatEnum.BLACK_WATER);
            habitat.setThFragmentName("blackwater");
            habitatRepo.save(habitat);
        }

        if (habitatRepo.findByName(HabitatEnum.BRACKISH_WATER).isEmpty()) {
            Habitat habitat = new Habitat();
            habitat.setName(HabitatEnum.BRACKISH_WATER);
            habitat.setThFragmentName("brackish-water");
            habitatRepo.save(habitat);
        }

        if (habitatRepo.findByName(HabitatEnum.REEF).isEmpty()) {
            Habitat habitat = new Habitat();
            habitat.setName(HabitatEnum.REEF);
            habitat.setThFragmentName("reef");
            habitatRepo.save(habitat);
        }
    }

    public Habitat findByTitle(String habitatName) throws HabitatNotFoundException {
        return habitatRepo.findHabitatByThFragmentName(habitatName).orElseThrow(HabitatNotFoundException::new);
    }
}
