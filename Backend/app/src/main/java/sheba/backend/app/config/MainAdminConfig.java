package sheba.backend.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.enums.UserRole;
import sheba.backend.app.repositories.AdminRepository;

@Configuration
public class MainAdminConfig {

    @Value("${mainadmin.username}")
    private String mainAdminUsername;

    @Value("${mainadmin.password}")
    private String mainAdminPassword;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;


    public MainAdminConfig(PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }
    @Bean
    public Admin mainAdmin() {
        if(adminRepository.findAdminByUsername(this.mainAdminUsername).isEmpty()){
        return adminRepository.save(Admin.builder()
                .username(mainAdminUsername)
                .password(passwordEncoder.encode(mainAdminPassword))
                .color("#BD10E0")
                .role(UserRole.MainAdmin)
                .build());
        }
        else{
            return adminRepository.findAdminByUsername(this.mainAdminUsername).get();
        }
    }
}
