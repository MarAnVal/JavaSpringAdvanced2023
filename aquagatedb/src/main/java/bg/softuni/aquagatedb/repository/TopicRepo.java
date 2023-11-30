package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    List<Topic> findTopicByDescriptionAndName(String description, String name);
}
