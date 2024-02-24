package shiba.backend.app.BL;

import org.springframework.stereotype.Service;
import shiba.backend.app.entities.Admin;
import shiba.backend.app.repositories.AdminRepository;

import java.util.List;

@Service
public class AdminBL {
//    private final AdminRepository adminDao;
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
//    public List<Admin> getAllAdmins() {
//        return adminDao.findAll();
//    }
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
