package sheba.backend.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sheba.backend.app.security.CustomAdminDetailsService;
import sheba.backend.app.security.JwtAuthenticationFilter;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    private final CustomAdminDetailsService adminDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private String[] authorized_urls = {"/login/**", "/register/**",Endpoints.TASK_ENDPOINT+"/**"};
//    private String[] authorized_urls = {"/login/**", "/register/**",
//            Endpoints.LOCATION_IMAGE_ENDPOINT + "/**", Endpoints.LOCATION_ENDPOINT+ "/**",
//            Endpoints.LOCATION_IMAGE_ENDPOINT + "/getimage/**",
//            Endpoints.OBJECTS_ENDPOINT+"/**",
//            Endpoints.OBJECT_IMAGE_ENDPOINT+"/**",
//            Endpoints.QUESTION_TASK_ENDPOINT+"/**",
//            Endpoints.TASK_ENDPOINT+"/**",
//            Endpoints.GAME_ENDPOINT+"/**",
//            Endpoints.ADMIN_ENDPOINT+"/**"};

    public SecurityConfig(CustomAdminDetailsService adminDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.adminDetailsService = adminDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers(authorized_urls)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(adminDetailsService)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e.accessDeniedHandler(
                        (request, response, accessDeniedException) -> response.setStatus(403)
                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .logout(l -> l
//                        .logoutUrl("/logout")
//                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler((request, response, authentication) ->
//                                SecurityContextHolder.clearContext()))
                .build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173", "http://localhost:8080/",
//                        "https://admin-platform-9l34-39s7xfz6x-mlk500s-projects.vercel.app/")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);


        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    public OncePerRequestFilter corsFilter() {
//        return new OncePerRequestFilter() {
//            @Override
//            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//             String origin = request.getHeader("Origin");
//                if (origin != null) {
//                    response.setHeader("Access-Control-Allow-Origin", origin);
//                }
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//                response.setHeader("Access-Control-Allow-Headers", "*");
//                filterChain.doFilter(request, response);
//            }
//        };
//    }
}

