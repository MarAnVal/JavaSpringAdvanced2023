package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {
    Picture findByPictureUrl(String pictureUrl);

    @Query("SELECT p FROM Picture p WHERE p.topic != null AND p.topic.approved = true ORDER BY p.id DESC LIMIT 2")
    Optional<List<Picture>> getLatestPictures();
}
