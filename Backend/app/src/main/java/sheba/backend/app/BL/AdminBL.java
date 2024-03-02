package sheba.backend.app.BL;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sheba.backend.app.DTO.AdminDTO;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.enums.UserRole;
import sheba.backend.app.exceptions.AdminAlreadyExists;
import sheba.backend.app.repositories.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminBL {

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    public AdminDTO createSectorAdmin(Admin admin) throws AdminAlreadyExists {
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (this.adminRepository.findAdminByUsername(admin.getUsername()).isPresent()) {
            throw new AdminAlreadyExists("Admin with username " + admin.getUsername() + " already exists");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(UserRole.SectorAdmin);
        Admin savedAdmin = this.adminRepository.save(admin);

        return convertToAdminDTO(savedAdmin);
    }

    private AdminDTO convertToAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminID(admin.getAdminID());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setColor(admin.getColor());
        adminDTO.setSector(admin.getSector());
        adminDTO.setRole(admin.getRole());
        return adminDTO;
    }

    public List<AdminDTO> getAllAdmins() {
        List<AdminDTO> adminDTOS;
        List<Admin> adminList = adminRepository.findAll();
        adminDTOS = adminList.stream().map(this::convertToAdminDTO).collect(Collectors.toList());
        return adminDTOS;
    }

    public AdminDTO updateAdmin(Admin admin){
        Admin currAdmin = adminRepository.findByAdminID(admin.getAdminID());
        if (currAdmin != null){
            currAdmin.setUsername(admin.getUsername());
            currAdmin.setSector(admin.getSector());
            currAdmin.setColor(admin.getColor());
            currAdmin.setRole(admin.getRole());
            currAdmin.getGamesList().clear();
            currAdmin.setGamesList(admin.getGamesList());
            adminRepository.save(currAdmin);
            return convertToAdminDTO(currAdmin);
        }
        else{
            throw new EntityNotFoundException("Admin with user name " + admin.getUsername() + "not found" );
        }
    }

    public void deleteAdmin(long id){
        Admin admin = adminRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Admin not found with ID: " + id));
        adminRepository.delete(admin);
    }
//
//    public AdminBL(AdminRepository adminDao) {
//        this.adminDao = adminDao;
//    }
//
//    public Admin addAdmin(Admin admin) {
//        return adminDao.save(admin);
//    }
//
//    public void deleteAdmin(int id) {
//        adminDao.deleteById(id);
//    }
//

//
//    public Admin updateAdmin(Integer id, Admin admin) {
//        Admin currAdmin = adminDao.findByAdminID(id);
//        if (currAdmin != null) {
//            currAdmin.setAdminID(admin.getAdminID());
//            currAdmin.setSector(admin.getSector());
//            currAdmin.setPassword(admin.getPassword());
//            currAdmin.setGamesList(admin.getGamesList());
//            return adminDao.save(currAdmin);
//        } else {
//            // throw error
//            return null;
//        }
//    }


}
