package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.repository.PictureRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Picture addPictureToNewTopic(MultipartFile file) throws IOException {
        Picture picture = new Picture();
        picture.setPictureUrl(cloudService.uploadImage(file));
        pictureRepo.save(picture);
        return pictureRepo.findByPictureUrl(picture.getPictureUrl());
    }

    public List<Picture> getLatestPictures() {
        //TODO check for null value
        return pictureRepo.getLatestPictures().orElse(null);
    }

    public void remove(Picture picture) {
        //TODO check for null or wrong value
        pictureRepo.delete(picture);
    }

    public void addPictureToExistingTopic(PictureAddDTO pictureAddDTO) {
        //TODO implement
    }

    public List<PictureView> mapPictureViewList(List<Picture> pictures) {
        //TODO check for null value
        return pictures.stream()
                .map(e -> modelMapper.map(e, PictureView.class))
                .collect(Collectors.toList());
    }
}
