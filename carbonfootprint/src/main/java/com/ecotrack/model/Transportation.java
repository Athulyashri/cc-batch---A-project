package com.ecotrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transportation")
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type; // car, bike, bus, train, flight

    @Column(nullable = false)
    private Double distance; // in km

    @Column(nullable = false)
    private Double carbonEmitted; // in kg CO2

    @Column(nullable = false)
    private LocalDate date;

    public Transportation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    public Double getCarbonEmitted() { return carbonEmitted; }
    public void setCarbonEmitted(Double carbonEmitted) { this.carbonEmitted = carbonEmitted; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
