package sheba.backend.app.BL;

import com.google.zxing.WriterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sheba.backend.app.entities.Location;
import sheba.backend.app.repositories.LocationRepository;
import sheba.backend.app.util.Endpoints;
import sheba.backend.app.util.QRCodeGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationBL {

    private final LocationRepository locationRepository;

    public Location craeteLocation(Location location) throws IOException, WriterException {
        String qrCodePath = generateLocationQRCode(location);
        location.setQRCode(qrCodePath);
        return locationRepository.save(location);
    }

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location updateLocation(Location location) throws IOException, WriterException {
        Location currLocation = locationRepository.findByLocationID(location.getLocationID());

        if (currLocation != null) {
            currLocation.setName(location.getName());
            currLocation.setDescription(location.getDescription());
            currLocation.setFloor(location.getFloor());
            currLocation.setLocationImg(location.getLocationImg());
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

}
