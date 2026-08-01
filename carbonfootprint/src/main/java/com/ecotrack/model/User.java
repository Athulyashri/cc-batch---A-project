package com.ecotrack.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role; // ROLE_USER or ROLE_ADMIN

    private Integer points = 0;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "streak_count")
    private Integer streakCount = 0;

    @Column(name = "last_activity_date")
    private java.time.LocalDate lastActivityDate;

    @Column(name = "dark_mode_enabled")
    private Boolean darkModeEnabled = false;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Integer getStreakCount() { return streakCount; }
    public void setStreakCount(Integer streakCount) { this.streakCount = streakCount; }
    public java.time.LocalDate getLastActivityDate() { return lastActivityDate; }
    public void setLastActivityDate(java.time.LocalDate lastActivityDate) { this.lastActivityDate = lastActivityDate; }
    public Boolean getDarkModeEnabled() { return darkModeEnabled; }
    public void setDarkModeEnabled(Boolean darkModeEnabled) { this.darkModeEnabled = darkModeEnabled; }
}
