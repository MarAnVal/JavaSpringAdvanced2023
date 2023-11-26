package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.data.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

}
