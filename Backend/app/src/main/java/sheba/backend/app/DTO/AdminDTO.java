package sheba.backend.app.DTO;

import sheba.backend.app.enums.UserRole;

public class AdminDTO {
    private long adminID;
    private String username;
    private String color;
    private String sector;
    private UserRole role;
    // Exclude gamesList or any sensitive information

    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
