package sheba.backend.app.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheba.backend.app.BL.AdminBL;
import sheba.backend.app.DTO.AdminDTO;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.exceptions.AdminAlreadyExists;
import sheba.backend.app.util.Endpoints;

@RestController
@RequestMapping(Endpoints.ADMIN_ENDPOINT)
@RequiredArgsConstructor
public class AdminController {

    private final AdminBL adminBL;


    @GetMapping("/demo")
    public ResponseEntity<String> testToken() {
        return ResponseEntity.ok("Hello authorized admin");
    }

    @PostMapping("create")
    public ResponseEntity<?> createSectorAdmin(@RequestBody Admin admin) {
        try {
            AdminDTO createdAdmin = adminBL.createSectorAdmin(admin);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch (AdminAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin with this username already exists.");
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllAdmins() {
        return ResponseEntity.ok(adminBL.getAllAdmins());
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin) {
        try {
            AdminDTO updatedAdmin = adminBL.updateAdmin(admin);
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Admin with user name " + admin.getUsername() + "not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable long id) {
        try {
            adminBL.deleteAdmin(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

//
//    public ResponseEntity<String> deleteAll() {
//        adminBL.deleteAll();
//        return new ResponseEntity<>("All admins deleted", HttpStatus.OK);
//    }

}
