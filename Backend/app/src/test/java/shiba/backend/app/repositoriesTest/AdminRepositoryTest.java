package shiba.backend.app.repositoriesTest;

import shiba.backend.app.entities.Admin;
import shiba.backend.app.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setSector("Physical Therapy");
        adminRepository.save(admin);
    }

    @Test
    public void testFindBySector_WhenAdminExists() {
        // when
        Admin found = adminRepository.findAdminBySector(admin.getSector());

        // then
        assertNotNull(found, "Admin should be found for the given sector");
        assertEquals(admin.getSector(), found.getSector(), "The found admin's sector should match the expected sector");
    }

    @Test
    public void testFindBySector_WhenAdminDoesNotExist() {
        Admin notFound = adminRepository.findAdminBySector("HR");

        assertNull(notFound, "No admin should be found for a non-existent sector");
    }
}
