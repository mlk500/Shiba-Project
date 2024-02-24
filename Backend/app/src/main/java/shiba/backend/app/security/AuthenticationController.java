package shiba.backend.app.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shiba.backend.app.DTO.UserLoginRequest;
import shiba.backend.app.DTO.UserRegistrationRequest;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRegistrationRequest request
    ) {
        System.out.println("Request is  " + request.toString());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        System.out.println("Request is  " + request.toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
