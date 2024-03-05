package sheba.backend.app.BL;

import com.google.zxing.WriterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.entities.Location;
import sheba.backend.app.entities.LocationImage;
import sheba.backend.app.repositories.LocationImageRepository;
import sheba.backend.app.repositories.LocationRepository;
import sheba.backend.app.util.Endpoints;
import sheba.backend.app.util.QRCodeGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationBL {

    private final LocationRepository locationRepository;
    private final LocationImageRepository locationImageRepository;

    public Location createLocationWithImage(Location location, MultipartFile imageFile) throws IOException, WriterException {
        String qrCodePath = generateLocationQRCode(location);
        location.setQRCode(qrCodePath);
        Location locationSaved = locationRepository.save(location);

        if (imageFile != null && !imageFile.isEmpty()) {
            LocationImage locationImage = uploadImageToFileSystem(imageFile, locationSaved);
            locationSaved.setLocationImage(locationImage);
            locationImage.setLocation(locationSaved);
            locationImageRepository.save(locationImage);
        }
        return locationSaved;
    }

    private LocationImage uploadImageToFileSystem(MultipartFile file, Location location) throws IOException {
        String filePath = Endpoints.LOCATION_IMAGE_PATH + file.getOriginalFilename();
        LocationImage locationImage = new LocationImage();
        locationImage.setName(file.getOriginalFilename());
        locationImage.setType(file.getContentType());
        locationImage.setImagePath(filePath);
        locationImage.setLocation(location);
        file.transferTo(new File(filePath));
        return locationImage;
    }


    public Location createLocation(Location location) throws IOException, WriterException {
        String qrCodePath = generateLocationQRCode(location);
        location.setQRCode(qrCodePath);
        return locationRepository.save(location);
    }


    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location updateLocation(Location location) throws IOException, WriterException {
        Location currLocation = locationRepository.findByLocationID(location.getLocationID());
        Path path = Paths.get(currLocation.getQRCode());
        if (Files.exists(path)) {
            Files.deleteIfExists(path);
        }
        if (currLocation != null) {
            currLocation.setName(location.getName());
            currLocation.setDescription(location.getDescription());
            currLocation.setFloor(location.getFloor());
            currLocation.getObjectsList().clear();
            currLocation.getObjectsList().addAll(location.getObjectsList());

            String qrCodePath = generateLocationQRCode(currLocation);
            currLocation.setQRCode(qrCodePath);
            currLocation.setQRCode(qrCodePath);

            return locationRepository.save(currLocation);
        } else {
            throw new EntityNotFoundException("Location not found with ID: " + location.getLocationID());
        }
    }


    public void deleteLocation(long id) {
        Location location = locationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Location not found with ID: " + id));
        locationRepository.delete(location);
    }


    private String generateLocationQRCode(Location location) throws IOException, WriterException {
        String qrName = Endpoints.QR_LOCATION + location.getName() + "_floor" + location.getFloor();
        String qrContent = "ID " + location.getLocationID() + "\n" + "Location Name " + location.getName() + "\n"
                + "Floor " + location.getFloor() + "\n" + "Description " + location.getDescription();
        return QRCodeGenerator.generateQRCode(qrName, qrContent, Endpoints.QR_LOCATION);
    }

    public Optional<Location> getLocationByID(long id) {
        return locationRepository.findById(id);
    }
}
