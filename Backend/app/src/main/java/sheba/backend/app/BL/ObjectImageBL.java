package sheba.backend.app.BL;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.entities.ObjectLocation;
import sheba.backend.app.entities.ObjectImage;
import sheba.backend.app.repositories.ObjectLocationRepository;
import sheba.backend.app.repositories.ObjectImageRepository;
import sheba.backend.app.util.Endpoints;
import sheba.backend.app.util.StoragePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectImageBL {
    private final ObjectImageRepository objectImageRepository;
    private final ObjectLocationRepository locationObjectRepository;

    public ObjectImageBL(ObjectImageRepository objectImageRepository, ObjectLocationRepository locationObjectRepository) {
        this.objectImageRepository = objectImageRepository;
        this.locationObjectRepository = locationObjectRepository;
    }

    public List<ObjectImage> addObjectImage(Long objectId, List<MultipartFile> images) throws IOException {
        ObjectLocation object = locationObjectRepository.findById(objectId)
                .orElseThrow(() -> new EntityNotFoundException("LocationObject not found with ID: " + objectId));

        Path objectDirectory = Paths.get(StoragePath.OBJECTS_IMAGES_PATH + File.separator + object.getObjectID());
        Files.createDirectories(objectDirectory);

        List<ObjectImage> savedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                continue;
            }

            String imageFileName = image.getOriginalFilename();
            Path imagePath = objectDirectory.resolve(imageFileName);
            image.transferTo(imagePath);

            ObjectImage objectImage = new ObjectImage();
            objectImage.setName(imageFileName);
            objectImage.setImagePath(imagePath.toString());
            objectImage.setObject(object);
            savedImages.add(objectImageRepository.save(objectImage));
        }

        return savedImages;
    }

}
