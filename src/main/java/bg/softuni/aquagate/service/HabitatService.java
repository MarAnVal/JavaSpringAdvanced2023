package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.entity.enumeration.HabitatEnum;
import bg.softuni.aquagate.repository.HabitatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitatService {
    private final HabitatRepo habitatRepo;

    @Autowired
    public HabitatService(HabitatRepo habitatRepo) {
        this.habitatRepo = habitatRepo;
    }

    public Habitat findHabitatByName(String habitat) {
        try {
            HabitatEnum habitatEnum = HabitatEnum.valueOf(habitat);
            return habitatRepo.findByName(habitatEnum).orElse(null);
        } catch (IllegalArgumentException e) {
            //TODO handle wrong parameter values and null return
            return null;
        }
    }

    public void init() {
        Habitat habitat = new Habitat();
        habitat.setName(HabitatEnum.FRESHWATER);
        habitat.setThFragmentName("freshwater");
        habitatRepo.save(habitat);

        habitat.setName(HabitatEnum.BLACK_WATER);
        habitat.setThFragmentName("black-water");
        habitatRepo.save(habitat);

        habitat.setName(HabitatEnum.BRACKISH_WATER);
        habitat.setThFragmentName("brackish-water");
        habitatRepo.save(habitat);

        habitat.setName(HabitatEnum.REEF);
        habitat.setThFragmentName("reef");
        habitatRepo.save(habitat);
    }
}
