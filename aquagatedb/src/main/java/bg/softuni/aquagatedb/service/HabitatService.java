package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.enumeration.HabitatEnum;
import bg.softuni.aquagatedb.repository.HabitatRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitatService {

    private final HabitatRepo habitatRepo;

    @Autowired
    public HabitatService(HabitatRepo habitatRepo) {
        this.habitatRepo = habitatRepo;
    }

    public Habitat findHabitatByName(String habitat) throws ObjectNotFoundException {
        try {
            HabitatEnum habitatEnum = HabitatEnum.valueOf(habitat);
            Habitat habitatEntity = habitatRepo.findHabitatByName(habitatEnum).orElse(null);
            if (habitatEntity == null) {
                throw new ObjectNotFoundException("Habitat not Found!");
            }

            return habitatEntity;

        } catch (IllegalArgumentException e) {
            throw new ObjectNotFoundException("Habitat not Found!");
        }
    }

    public boolean init() {
        boolean doneInit = false;
        for (HabitatEnum value : HabitatEnum.values()) {
            if (habitatRepo.findHabitatByName(value).isEmpty()) {
                Habitat habitat = new Habitat();
                habitat.setName(value);
                habitatRepo.save(habitat);
                doneInit = true;
            }
        }
        return doneInit;
    }
}
