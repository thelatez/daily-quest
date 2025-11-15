package backend.daily_quest.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.*;

@Entity
public class AppUser {
    @Id 
    @GeneratedValue
    private Long appUser_id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 4, max = 100, message = "Password must be 4-100 characters")
    private String passwordHash;

    @NotBlank
    private String role; // "USER" or "ADMIN"

    @OneToMany(mappedBy = "appUser")
    private List<Daily> dailies = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getAppUser_id() {
        return appUser_id;
    }

    public void setAppUser_id(Long appUser_id) {
        this.appUser_id = appUser_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }
    
}
