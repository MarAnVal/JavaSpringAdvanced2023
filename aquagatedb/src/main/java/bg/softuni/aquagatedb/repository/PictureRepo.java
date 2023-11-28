package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.data.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {

    Optional<Picture> findByPictureUrl(String pictureUrl);
}
