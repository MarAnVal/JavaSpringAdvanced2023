package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t ORDER BY size(t.comments) DESC")
    Optional<Topic> getMostCommented();
}
