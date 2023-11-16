package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.entity.enumeration.HabitatEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitatRepo extends JpaRepository<Habitat, Long> {
    Optional<Habitat> findByName(HabitatEnum Habitat);
}
