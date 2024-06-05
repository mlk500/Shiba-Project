package sheba.backend.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Bean
    public String jwtSecret() {
        return jwtSecretKey;
    }
}
