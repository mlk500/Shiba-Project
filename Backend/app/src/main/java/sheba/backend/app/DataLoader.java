package sheba.backend.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sheba.backend.app.BL.LocationBL;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.entities.Location;
import sheba.backend.app.enums.UserRole;
import sheba.backend.app.repositories.AdminRepository;

@Configuration
public class DataLoader {

    private final AdminRepository adminRepository;

    private final LocationBL locationBL;

    private final PasswordEncoder passwordEncoder;

    public DataLoader(PasswordEncoder passwordEncoder, AdminRepository adminRepository, LocationBL locationBL) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.locationBL = locationBL;
    }

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            // Insert admin data
            Admin admin = new Admin();
            admin.setUsername("newadmin");
            admin.setPassword(passwordEncoder.encode("password123"));
            admin.setSector("Health");
            admin.setRole(UserRole.MainAdmin);
            adminRepository.save(admin);

            Admin admin1 = new Admin();
            admin1.setUsername("secadmin1");
            admin1.setPassword(passwordEncoder.encode("admin1"));
            admin1.setSector("Health");
            admin1.setRole(UserRole.SectorAdmin);
            adminRepository.save(admin1);

            Admin admin2 = new Admin();
            admin2.setUsername("secadmin2");
            admin2.setPassword(passwordEncoder.encode("admin2"));
            admin2.setSector("PT");
            admin2.setRole(UserRole.SectorAdmin);
            adminRepository.save(admin2);

            Location kitchen = new Location();
            kitchen.setName("Kitchen");
            kitchen.setDescription("a kitchen that is located next to PT room");
            kitchen.setFloor(2);
            locationBL.createLocation(kitchen);

            Location ptRoom = new Location();
            ptRoom.setName("PT");
            ptRoom.setDescription("PT room with training machines");
            ptRoom.setFloor(2);
            locationBL.createLocation(ptRoom);

            Location playroom = new Location();
            playroom.setName("Playroom");
            playroom.setDescription("A room with board games");
            playroom.setFloor(3);
            locationBL.createLocation(playroom);
        };
    }
}
