package shiba.backend.app.BLTest;

import shiba.backend.app.BL.AdminBL;
import shiba.backend.app.entities.Admin;
import shiba.backend.app.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminBLTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminBL adminBL;

    private Admin admin;

    @BeforeEach
    void setUp() {
        // Common setup for each test case
        admin = new Admin();
        admin.setSector("Physical Therapy");
    }

    @Test
    public void testGetAdminBySector_Success() {
        when(adminRepository.findAdminBySector("Physical Therapy")).thenReturn(admin);

        Admin found = adminBL.getAdminBySector("Physical Therapy");

        assertNotNull(found, "Admin should not be null");
        assertEquals("Physical Therapy", found.getSector(), "Sector should match the expected value");
    }

    @Test
    public void testGetAdminBySector_NotFound() {

        when(adminRepository.findAdminBySector("HR")).thenReturn(null);

        Admin found = adminBL.getAdminBySector("HR");

        assertNull(found, "Admin should be null for an unknown sector");
    }

}
