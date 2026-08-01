package com.ecotrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String dietType; // vegan, vegetarian, non-vegetarian

    @Column(nullable = false)
    private Double carbonEmitted; // in kg CO2

    @Column(nullable = false)
    private LocalDate date;

    public Food() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }
    public Double getCarbonEmitted() { return carbonEmitted; }
    public void setCarbonEmitted(Double carbonEmitted) { this.carbonEmitted = carbonEmitted; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
