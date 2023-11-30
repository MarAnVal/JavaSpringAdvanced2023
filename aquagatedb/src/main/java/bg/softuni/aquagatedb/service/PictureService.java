package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.model.entity.Picture;
import bg.softuni.aquagatedb.repository.PictureRepo;
import bg.softuni.aquagatedb.web.error.PictureNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    private final PictureRepo pictureRepo;

    @Autowired
    public PictureService(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public Picture addPicture(String pictureUrl) throws PictureNotFoundException {
        if (pictureUrl == null) {
            pictureUrl = "/images/picture-not-found.jpg";
        }
        if (pictureRepo.findByPictureUrl(pictureUrl).isPresent()) {
            return pictureRepo.findByPictureUrl(pictureUrl).get();
        } else {
            Picture picture = new Picture();
            picture.setPictureUrl(pictureUrl);
            pictureRepo.save(picture);
            return pictureRepo.findByPictureUrl(pictureUrl).orElseThrow(PictureNotFoundException::new);
        }
    }
}
