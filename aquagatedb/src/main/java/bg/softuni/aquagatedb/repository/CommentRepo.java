package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllByContextAndTopicIdAndAuthorIdOrderByIdDesc(String context, Long topicId, Long authorId);

    List<Comment> findAllByTopicId(Long id);
}
