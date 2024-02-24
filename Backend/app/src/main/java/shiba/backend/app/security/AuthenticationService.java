package shiba.backend.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shiba.backend.app.DTO.UserLoginRequest;
import shiba.backend.app.DTO.UserRegistrationRequest;
import shiba.backend.app.entities.Admin;
import shiba.backend.app.repositories.AdminRepository;

@Service
public class AuthenticationService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserRegistrationRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setSector(request.getSector());
        admin.setRole(request.getRole());
        adminRepository.save(admin);
        UserDetails userDetails = new CustomAdminDetails(admin);
        final String token = jwtService.generateToken(userDetails);
        System.out.println("token is    " + token);
        return new AuthenticationResponse(token, "User registered successfully");
    }

    public AuthenticationResponse authenticate(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(token, "Login successful");
    }
}
