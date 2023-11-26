package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.data.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {
    List<Picture> findPicturesByTopicId(Long id);
}
