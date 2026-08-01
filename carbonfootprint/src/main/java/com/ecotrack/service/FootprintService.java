package com.ecotrack.service;

import com.ecotrack.model.*;
import com.ecotrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FootprintService {
    @Autowired
    private TransportationRepository transportRepo;
    
    @Autowired
    private ElectricityRepository electricityRepo;
    
    @Autowired
    private FoodRepository foodRepo;
    
    @Autowired
    private WasteRepository wasteRepo;

    // Carbon emission factors (approximate kg CO2 per unit)
    private static final double EMISSION_CAR = 0.192;
    private static final double EMISSION_BIKE = 0.0;
    private static final double EMISSION_BUS = 0.105;
    private static final double EMISSION_TRAIN = 0.041;
    private static final double EMISSION_FLIGHT = 0.255;
    
    private static final double EMISSION_ELECTRICITY_KWH = 0.92;
    
    private static final double EMISSION_VEGAN = 2.0;
    private static final double EMISSION_VEG = 3.0;
    private static final double EMISSION_NON_VEG = 7.2;

    public Transportation addTransportation(User user, String type, Double distance, LocalDate date) {
        double carbonEmitted = 0.0;
        switch (type.toLowerCase()) {
            case "car": carbonEmitted = distance * EMISSION_CAR; break;
            case "bike": carbonEmitted = distance * EMISSION_BIKE; break;
            case "bus": carbonEmitted = distance * EMISSION_BUS; break;
            case "train": carbonEmitted = distance * EMISSION_TRAIN; break;
            case "flight": carbonEmitted = distance * EMISSION_FLIGHT; break;
        }
        Transportation t = new Transportation();
        t.setUser(user);
        t.setType(type);
        t.setDistance(distance);
        t.setCarbonEmitted(carbonEmitted);
        t.setDate(date);
        return transportRepo.save(t);
    }

    public Electricity addElectricity(User user, Double kwhUsed, LocalDate date) {
        Electricity e = new Electricity();
        e.setUser(user);
        e.setKwhUsed(kwhUsed);
        e.setCarbonEmitted(kwhUsed * EMISSION_ELECTRICITY_KWH);
        e.setDate(date);
        return electricityRepo.save(e);
    }

    public Food addFood(User user, String dietType, LocalDate date) {
        double carbonEmitted = 0.0;
        switch (dietType.toLowerCase()) {
            case "vegan": carbonEmitted = EMISSION_VEGAN; break;
            case "vegetarian": carbonEmitted = EMISSION_VEG; break;
            case "non-vegetarian": carbonEmitted = EMISSION_NON_VEG; break;
        }
        Food f = new Food();
        f.setUser(user);
        f.setDietType(dietType);
        f.setCarbonEmitted(carbonEmitted);
        f.setDate(date);
        return foodRepo.save(f);
    }

    public Waste addWaste(User user, String wasteType, Double weightKg, LocalDate date) {
        double carbonEmitted = 0.0;
        if(wasteType.equalsIgnoreCase("landfill")) {
            carbonEmitted = weightKg * 0.5; // simple approximation
        }
        Waste w = new Waste();
        w.setUser(user);
        w.setWasteType(wasteType);
        w.setWeightKg(weightKg);
        w.setCarbonEmitted(carbonEmitted);
        w.setDate(date);
        return wasteRepo.save(w);
    }
    
    public double getTotalFootprint(Long userId) {
        double total = 0;
        for (Transportation t : transportRepo.findByUserId(userId)) total += t.getCarbonEmitted();
        for (Electricity e : electricityRepo.findByUserId(userId)) total += e.getCarbonEmitted();
        for (Food f : foodRepo.findByUserId(userId)) total += f.getCarbonEmitted();
        for (Waste w : wasteRepo.findByUserId(userId)) total += w.getCarbonEmitted();
        return total;
    }
}
