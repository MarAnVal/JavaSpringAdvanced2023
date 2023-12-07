package bg.softuni.aquagateclient.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = File
                .createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return cloudinary.uploader()
                .upload(file, new HashMap())
                .get("url").toString();
    }
}
