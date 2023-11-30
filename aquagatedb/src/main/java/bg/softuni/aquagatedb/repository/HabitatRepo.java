package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.model.entity.Habitat;
import bg.softuni.aquagatedb.model.entity.enumeration.HabitatEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitatRepo extends JpaRepository<Habitat, Long> {

    Optional<Habitat> findHabitatByName(HabitatEnum value);
}
