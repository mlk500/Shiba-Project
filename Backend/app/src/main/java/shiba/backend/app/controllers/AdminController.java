package shiba.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiba.backend.app.BL.AdminBL;
import shiba.backend.app.entities.Admin;
import shiba.backend.app.util.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.ADMIN_ENDPOINT)
public class AdminController {

    private final AdminBL adminBL;

    public AdminController(AdminBL adminBL) {
        this.adminBL = adminBL;
    }

    @PostMapping("add")
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminBL.addAdmin(admin);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAdmin(@PathVariable int id) {
        adminBL.deleteAdmin(id);
    }

    @GetMapping("getAll")
    public List<Admin> getAllAdmins() {
        return adminBL.getAllAdmins();
    }

    @GetMapping("getAdmin")
    public Admin getAdmin(@RequestBody int id) {
        return adminBL.getAdmin(id);
    }

    @GetMapping("getAdminBySector")
    public Admin getAdminBySector(@RequestParam String sector) {
        return adminBL.getAdminBySector(sector);
    }

    @PutMapping("updateAdmin/{id}")
    public Admin updateNote(@PathVariable Integer id, @RequestBody Admin admin) {
        return adminBL.updateAdmin(id, admin);
    }

    public ResponseEntity<String> deleteAll() {
        adminBL.deleteAll();
        return new ResponseEntity<>("All admins deleted", HttpStatus.OK);
    }

}
