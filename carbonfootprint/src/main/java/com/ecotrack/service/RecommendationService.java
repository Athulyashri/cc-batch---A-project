package com.ecotrack.service;

import com.ecotrack.model.Transportation;
import com.ecotrack.repository.TransportationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private TransportationRepository transportRepo;
    
    // In a real app, this would be more complex and personalized
    public List<String> getRecommendations(Long userId) {
        List<String> recommendations = new ArrayList<>();
        
        List<Transportation> transports = transportRepo.findByUserId(userId);
        boolean usesCar = transports.stream().anyMatch(t -> t.getType().equalsIgnoreCase("car"));
        
        if(usesCar) {
            recommendations.add("Consider carpooling or using public transit to reduce your transportation footprint.");
        }
        
        recommendations.add("Switch to LED bulbs to reduce your electricity consumption by up to 80%.");
        recommendations.add("Try a plant-based meal once a week to significantly lower your carbon emissions from food.");
        recommendations.add("Start a compost bin to minimize your landfill waste and create nutrient-rich soil.");
        
        return recommendations;
    }
}
