package sheba.backend.app.BL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.entities.Location;
import sheba.backend.app.entities.ObjectLocation;
import sheba.backend.app.entities.ObjectImage;
import sheba.backend.app.repositories.ObjectLocationRepository;
import sheba.backend.app.repositories.LocationRepository;
import sheba.backend.app.repositories.ObjectImageRepository;
import sheba.backend.app.util.Endpoints;

import jakarta.persistence.EntityNotFoundException;
import sheba.backend.app.util.StoragePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectLocationBL {
    private final ObjectLocationRepository locationObjectRepository;
    private final LocationRepository locationRepository;
    private final ObjectImageRepository objectImageRepository;

    public ObjectLocationBL(ObjectLocationRepository locationObjectRepository, LocationRepository locationRepository, ObjectImageRepository objectImageRepository) {
        this.locationObjectRepository = locationObjectRepository;
        this.locationRepository = locationRepository;
        this.objectImageRepository = objectImageRepository;
    }

    public ObjectLocation createLocationObject(Long locationId, ObjectLocation locationObject, List<MultipartFile> images) throws IOException {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + locationId));

        locationObject.setLocation(location);
        ObjectLocation savedObject = locationObjectRepository.save(locationObject);

        if (images != null && !images.isEmpty()) {
            Path objectDirectory = Paths.get(StoragePath.OBJECTS_IMAGES_PATH + File.separator + savedObject.getObjectID());
            Files.createDirectories(objectDirectory);

            List<ObjectImage> objectImages = new ArrayList<>();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String imageFileName = image.getOriginalFilename();
                    Path imagePath = objectDirectory.resolve(imageFileName);
                    image.transferTo(imagePath);

                    ObjectImage objectImage = new ObjectImage();
                    objectImage.setName(imageFileName);
                    objectImage.setImagePath(imagePath.toString());
                    objectImage.setObject(savedObject);
                    objectImages.add(objectImageRepository.save(objectImage));
                }
            }
            savedObject.setObjectImages(objectImages);
        }

        return savedObject;
    }


}
