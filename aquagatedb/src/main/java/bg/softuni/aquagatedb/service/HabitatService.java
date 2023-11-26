package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.data.entity.Habitat;
import bg.softuni.aquagatedb.data.entity.enumeration.HabitatEnum;
import bg.softuni.aquagatedb.repository.HabitatRepo;
import bg.softuni.aquagatedb.web.error.HabitatNotFoundException;
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
        return habitatRepo.findHabitatByName(HabitatEnum.valueOf(habitat)).orElseThrow(HabitatNotFoundException::new);
    }

    public void init() {
        for (HabitatEnum value : HabitatEnum.values()) {
            if(habitatRepo.findHabitatByName(value).isEmpty()){
                Habitat habitat = new Habitat();
                habitat.setName(value);
                habitatRepo.save(habitat);
            }
        }
    }
}
