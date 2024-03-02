package sheba.backend.app.controllers;

import com.google.zxing.WriterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheba.backend.app.BL.LocationBL;
import sheba.backend.app.entities.Location;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;

@RestController
@RequestMapping(Endpoints.LOCATION_ENDPOINT)
@RequiredArgsConstructor
public class LocationController {
    private final LocationBL locationBL;

    @PostMapping("create")
    public ResponseEntity<?> addLocation(@RequestBody Location location) throws IOException, WriterException {
        return ResponseEntity.ok(locationBL.craeteLocation(location));
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(locationBL.getAll());
    }

    @PutMapping("update")
    public ResponseEntity<?> updateLocation(@RequestBody Location location) {
        try {
            Location updatedLocation = locationBL.updateLocation(location);
            return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // return a 404 Not Found
            return new ResponseEntity<>("Location not found with ID: " + location.getLocationID(), HttpStatus.NOT_FOUND);
        } catch (IOException | WriterException e) {
            return new ResponseEntity<>("Error generating QR code: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable long id) {
        try {
            locationBL.deleteLocation(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


}
