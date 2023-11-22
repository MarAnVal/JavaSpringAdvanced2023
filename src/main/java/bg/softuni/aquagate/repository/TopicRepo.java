package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE t.approved = true ORDER BY size(t.comments) DESC")
    Optional<Topic> getMostCommented();

    Optional<List<Topic>> findAllByApproved(boolean approved);

    @Query("SELECT t FROM Topic t ORDER BY t.id DESC LIMIT 1")
    Optional<Topic> findLatestTopic();
    @Query("SELECT t FROM Topic t WHERE t.approved = true AND t.author.id =: id")
    Optional<List<Topic>> findAllApprovedByUserId(Long id);
}
