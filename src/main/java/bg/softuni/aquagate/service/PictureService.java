package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.repository.PictureRepo;
import bg.softuni.aquagate.web.error.PictureNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureService {
    private final PictureRepo pictureRepo;
    private final CloudService cloudService;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureService(CloudService cloudService, PictureRepo pictureRepo, ModelMapper modelMapper) {
        this.cloudService = cloudService;
        this.pictureRepo = pictureRepo;
        this.modelMapper = modelMapper;
    }

    public Picture addPictureToNewTopic(MultipartFile file) throws IOException, PictureNotFoundException {
        Picture picture = new Picture();
        picture.setPictureUrl(cloudService.uploadImage(file));
        pictureRepo.save(picture);
        return pictureRepo.findByPictureUrl(picture.getPictureUrl()).orElseThrow(PictureNotFoundException::new);
    }

    public List<Picture> getLatestPictures() throws PictureNotFoundException {
        List<Picture> pictures = pictureRepo.getLatestPictures().orElse(new ArrayList<>());
        if(pictures.isEmpty()){
           throw new PictureNotFoundException();
        } else if(pictures.size()<2){
            pictures.add(getNoPicturesUnit().get(0));
        }
        return pictures;
    }

    public void remove(Picture picture) throws PictureNotFoundException {
        pictureRepo.findById(picture.getId()).orElseThrow(PictureNotFoundException::new);
        pictureRepo.delete(picture);
    }

    public Picture addPictureToExistingTopic(PictureAddDTO pictureAddDTO, Topic topic) throws IOException,
            PictureNotFoundException {
        Picture picture = new Picture();
        picture.setPictureUrl(cloudService.uploadImage(pictureAddDTO.getPictureFile()));
        picture.setTopic(topic);
        pictureRepo.save(picture);
        return pictureRepo.findByPictureUrl(picture.getPictureUrl()).orElseThrow(PictureNotFoundException::new);
    }

    public List<PictureView> mapPictureViewList(List<Picture> pictures) {
        return pictures.stream()
                .map(e -> modelMapper.map(e, PictureView.class))
                .collect(Collectors.toList());
    }

    public List<Picture> getNoPicturesUnit() {
        Picture picture = new Picture();
        picture.setPictureUrl("/images/picture-not-found.png");
        return List.of(picture);
    }
    public List<Picture> getNoGalleryPicturesUnit() {
        Picture firstPicture = new Picture();
        firstPicture.setPictureUrl("/images/galery1.webp");
        Picture secondPicture = new Picture();
        secondPicture.setPictureUrl("/images/galery2.jpg");
        return List.of(firstPicture, secondPicture);
    }
}
