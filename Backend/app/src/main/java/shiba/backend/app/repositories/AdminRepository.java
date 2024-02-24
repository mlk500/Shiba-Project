package shiba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shiba.backend.app.entities.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findAdminByUsername(String username);

//    Admin findByAdminID(long adminID);

//    Admin findAdminBySector(String sector);
}
