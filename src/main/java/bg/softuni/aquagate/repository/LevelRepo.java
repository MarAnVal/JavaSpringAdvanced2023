package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Level;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepo extends JpaRepository<Level, Long> {

    Level findLevelByName(LevelEnum levelEnum);
}
