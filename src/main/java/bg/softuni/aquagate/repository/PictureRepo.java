package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {
}
