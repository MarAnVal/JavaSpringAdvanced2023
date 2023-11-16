package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Level;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import bg.softuni.aquagate.repository.LevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {
    private final LevelRepo levelRepo;

    @Autowired
    public LevelService(LevelRepo levelRepo) {
        this.levelRepo = levelRepo;
    }

    public void init(){
        for (LevelEnum value : LevelEnum.values()) {
            if(levelRepo.findLevelByName(value) == null){
                Level level = new Level();
                level.setName(value);
                levelRepo.save(level);
            }
        }
    }

    public Level findLevelByName(LevelEnum levelEnum) {
        return levelRepo.findLevelByName(levelEnum);
    }
}
