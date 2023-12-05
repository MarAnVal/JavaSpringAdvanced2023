package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PictureService {

    private final PictureRepo pictureRepo;

    @Autowired
    public PictureService(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public Picture addPicture(String pictureUrl) throws ObjectNotFoundException {
        if (pictureUrl == null || pictureUrl.isBlank()) {
            pictureUrl = "/images/picture-not-found.jpg";
        }

        Picture picture = new Picture();
        picture.setPictureUrl(pictureUrl);
        pictureRepo.save(picture);

        Picture lastPicture = pictureRepo.findAll()
                .stream()
                .max((e1, e2) -> {
                    Long id1 = e1.getId();
                    Long id2 = e2.getId();
                    return id1.compareTo(id2);
                })
                .orElse(null);

        if (lastPicture == null ||
                !Objects.equals(lastPicture.getPictureUrl(), picture.getPictureUrl())) {

            throw new ObjectNotFoundException("Problem with uploading the picture! Please try again!");
        }

        return lastPicture;
    }
}
