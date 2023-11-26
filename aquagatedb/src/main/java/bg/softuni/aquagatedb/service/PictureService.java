package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.data.entity.Picture;
import bg.softuni.aquagatedb.repository.PictureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {
    private final PictureRepo pictureRepo;

    @Autowired
    public PictureService(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public void add(String pictureUrl) {
        if (pictureUrl == null) {
            pictureUrl = "/images/picture-not-found.jpg";
        }
        Picture picture = new Picture();
        picture.setPictureUrl(pictureUrl);
        pictureRepo.save(picture);
    }

    public void remove(Picture picture) {
        pictureRepo.delete(picture);
    }

    public void removeByTopicId(Long id) {
        List<Picture> picturesByTopicId = pictureRepo.findPicturesByTopicId(id);
        pictureRepo.deleteAll(picturesByTopicId);
    }
}
