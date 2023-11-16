package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatRepo extends JpaRepository<Habitat, Long> {
}
