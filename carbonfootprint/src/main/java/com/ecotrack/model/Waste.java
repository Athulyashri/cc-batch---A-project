package com.ecotrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "waste")
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String wasteType; // recycling, compost, landfill

    @Column(nullable = false)
    private Double weightKg;

    @Column(nullable = false)
    private Double carbonEmitted; // in kg CO2

    @Column(nullable = false)
    private LocalDate date;

    public Waste() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getWasteType() { return wasteType; }
    public void setWasteType(String wasteType) { this.wasteType = wasteType; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public Double getCarbonEmitted() { return carbonEmitted; }
    public void setCarbonEmitted(Double carbonEmitted) { this.carbonEmitted = carbonEmitted; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
