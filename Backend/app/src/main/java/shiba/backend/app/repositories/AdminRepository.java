package shiba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shiba.backend.app.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAdminID(long adminID);

    Admin findAdminBySector(String sector);
}
