package sheba.backend.app.BL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.entities.Location;
import sheba.backend.app.entities.LocationImage;
import sheba.backend.app.exceptions.LocationMissingInLocationImage;
import sheba.backend.app.repositories.LocationImageRepository;
import sheba.backend.app.util.Endpoints;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class LocationImageBL {

    private final LocationImageRepository locationImageRepository;
    private final LocationBL locationBL;

    public LocationImageBL(LocationImageRepository locationImageRepository, LocationBL locationBL) {
        this.locationImageRepository = locationImageRepository;
        this.locationBL = locationBL;
    }

    public LocationImage uploadImageToFileSystem(MultipartFile file, Long locationID) throws IOException, LocationMissingInLocationImage {
        String filePath = Endpoints.LOCATION_IMAGE_PATH + file.getOriginalFilename();
        LocationImage locationImage = new LocationImage();
        locationImage.setName(file.getOriginalFilename());
        locationImage.setType(file.getContentType());
        locationImage.setImagePath(filePath);
        Optional<Location> location = locationBL.getLocationByID(locationID);
        if(location.isEmpty()){
            throw new LocationMissingInLocationImage("Location Image must belong to and Existing Location");
        }
        location.ifPresent(locationImage::setLocation);
        file.transferTo(new File(filePath));
        return locationImageRepository.save(locationImage);
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<LocationImage> imageData = locationImageRepository.findByName(fileName);
        if (imageData.isPresent()) {
            String imagePath = imageData.get().getImagePath();
            return Files.readAllBytes(new File(imagePath).toPath());
        }
        return null;
    }
}
//    public String uploadImage(MultipartFile file) throws IOException {
//        String formatName = determineFormat(file.getContentType());
//
//        byte[] compressedImage = ImageActions.compress(file.getBytes());
//
//        LocationImage imageData = locationImageRepository.save(LocationImage.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(compressedImage).build());
//
//        return imageData != null ? "file uploaded successfully : " + file.getOriginalFilename() : null;
//    }
//
//    public byte[] downloadImage(String fileName) {
//        Optional<LocationImage> locationImage = locationImageRepository.findByName(fileName);
//        System.out.println("found it");
//        try {
//            return locationImage.map(image -> {
//                try {
//                    return ImageActions.decompress(image.getImageData());
//                } catch (IOException | DataFormatException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }).orElse(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    private String determineFormat(String contentType) {
//        switch (contentType) {
//            case "image/jpeg":
//                return "JPEG";
//            case "image/png":
//                return "PNG";
//            case "image/webp":
//                return "WEBP";
//            default:
//                return "PNG";
//        }
//
//    }
//}
