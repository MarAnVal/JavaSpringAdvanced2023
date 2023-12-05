package bg.softuni.aquagatedb.repository;

import bg.softuni.aquagatedb.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {

}
