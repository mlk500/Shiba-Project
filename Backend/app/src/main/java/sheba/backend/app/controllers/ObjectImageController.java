package sheba.backend.app.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.BL.ObjectImageBL;
import sheba.backend.app.entities.Location;
import sheba.backend.app.entities.ObjectImage;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(Endpoints.OBJECT_IMAGE_ENDPOINT)
public class ObjectImageController {
    private final ObjectImageBL objectImageBL;

    public ObjectImageController(ObjectImageBL objectImageBL) {
        this.objectImageBL = objectImageBL;
    }

    @PostMapping(value = "add/{objectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addObjectImage(@PathVariable Long objectId, @RequestParam("image") List<MultipartFile> images) {
        try {
            List<ObjectImage> savedImage = objectImageBL.addObjectImage(objectId, images);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving object image: " + e.getMessage());
        }
    }
}
