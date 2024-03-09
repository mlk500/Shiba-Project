package sheba.backend.app.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.BL.ObjectLocationBL;
import sheba.backend.app.entities.ObjectLocation;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(Endpoints.OBJECTS_ENDPOINT)
@RequiredArgsConstructor
public class ObjectLocationController {

    private final ObjectLocationBL locationObjectBL;

    @PostMapping(value = "/create/{locationId}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> addLocationObject(
            @PathVariable Long locationId,
            @RequestPart("locationObject") ObjectLocation locationObject,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ObjectLocation createdLocationObject = locationObjectBL.createLocationObject(locationId, locationObject, images);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving object images: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
