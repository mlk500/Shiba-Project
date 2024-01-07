package shiba.backend.app.controllersTest;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import shiba.backend.app.BL.AdminBL;
import shiba.backend.app.entities.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shiba.backend.app.util.Constants;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(shiba.backend.app.controllers.AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminBL adminBL;

    @Mock
    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setSector("Physical Therapy");
    }

    @Test
    public void testAddAdmin() throws Exception {
        given(adminBL.addAdmin(any(Admin.class))).willReturn(admin);

        mockMvc.perform(post(Constants.ADMIN_ENDPOINT + "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sector\":\"Physical Therapy\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sector").value("Physical Therapy"));
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        willDoNothing().given(adminBL).deleteAdmin(anyInt());

        mockMvc.perform(delete(Constants.ADMIN_ENDPOINT + "delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        List<Admin> allAdmins = Arrays.asList(admin);
        given(adminBL.getAllAdmins()).willReturn(allAdmins);

        mockMvc.perform(get(Constants.ADMIN_ENDPOINT + "getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sector").value("Physical Therapy"));
    }

    @Test
    public void testGetAdmin() throws Exception {
        given(adminBL.getAdmin(anyInt())).willReturn(admin);

        mockMvc.perform(get(Constants.ADMIN_ENDPOINT + "getAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")) // Example request body with ID
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sector").value("Physical Therapy"));
    }

    @Test
    public void testGetAdminBySector() throws Exception {
        given(adminBL.getAdminBySector(anyString())).willReturn(admin);

        mockMvc.perform(get(Constants.ADMIN_ENDPOINT + "getAdminBySector")
                        .param("sector", "Physical Therapy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sector").value("Physical Therapy"));
    }

    @Test
    public void testUpdateAdmin() throws Exception {
        Admin updatedAdmin = new Admin();
        updatedAdmin.setSector("Updated Sector");

        given(adminBL.updateAdmin(anyInt(), any(Admin.class))).willReturn(updatedAdmin);

        mockMvc.perform(put(Constants.ADMIN_ENDPOINT + "updateAdmin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sector\":\"Updated Sector\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sector").value("Updated Sector"));
    }


    @Test
    @PostMapping("deleteAll")
    public ResponseEntity<String> deleteAll() {
        adminBL.deleteAll();
        return new ResponseEntity<>("All admins deleted", HttpStatus.OK);
    }

}
