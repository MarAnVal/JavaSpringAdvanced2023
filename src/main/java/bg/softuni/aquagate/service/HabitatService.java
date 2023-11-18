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
    //TODO decide how to be done general init() of habitats with data included atm in habitats htmls
    public void init(){
        for (HabitatEnum value : HabitatEnum.values()) {
            if(habitatRepo.findByName(value)==null){
                Habitat habitat = new Habitat();
                habitat.setName(value);
                habitatRepo.save(habitat);
            }
        }
    }

    public Habitat findHabitatByName(HabitatEnum habitat) {
        return habitatRepo.findByName(habitat);
    }
}
