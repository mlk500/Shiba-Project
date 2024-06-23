package sheba.backend.app.security;

import sheba.backend.app.entities.Admin;

public class AuthenticationResponse {

    private String token;
    private String message;
    private Admin admin;
    public AuthenticationResponse(String token, String message, Admin admin) {
        this.token = token;
        this.message = message;
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public Admin getAdmin() {return admin;}
}
