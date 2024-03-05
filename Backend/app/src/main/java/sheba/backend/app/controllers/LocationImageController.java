package sheba.backend.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.BL.LocationImageBL;
import sheba.backend.app.entities.LocationImage;
import sheba.backend.app.exceptions.LocationMissingInLocationImage;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;

@RestController
@RequestMapping(Endpoints.LOCATION_IMAGE_ENDPOINT)
public class LocationImageController {

    private final LocationImageBL locationImageBL;

    public LocationImageController(LocationImageBL locationImageBL) {
        this.locationImageBL = locationImageBL;
    }

    @PostMapping("uploadImage/{locationID}")
    public ResponseEntity<?> uploadImage(@PathVariable long locationID, @RequestParam("image") MultipartFile file) throws IOException {
        LocationImage uploadImage = null;
        try {
            uploadImage = locationImageBL.uploadImageToFileSystem(file, locationID);
        } catch (LocationMissingInLocationImage e) {
            return new ResponseEntity<>("Location Image must belong to and Existing Location" , HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("getImage/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws IOException {
        byte[] imageData=locationImageBL.downloadImageFromFileSystem(fileName);
        if(imageData != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
                    .body(imageData);
        }
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);

    }
}
