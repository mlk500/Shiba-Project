package sheba.backend.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.enums.UserRole;

@Configuration
public class MainAdminConfig {

    @Value("${mainadmin.username}")
    private String mainAdminUsername;

    @Value("${mainadmin.password}")
    private String mainAdminPassword;
    private final PasswordEncoder passwordEncoder;

    public MainAdminConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public Admin mainAdmin() {
        return Admin.builder()
                .username(mainAdminUsername)
                .password(passwordEncoder.encode(mainAdminPassword))
                .role(UserRole.MainAdmin)
                .build();
    }
}
