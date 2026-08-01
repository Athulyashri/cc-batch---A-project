package com.ecotrack.controller;

import com.ecotrack.model.User;
import com.ecotrack.repository.UserRepository;
import com.ecotrack.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FootprintService footprintService;

    // Secure this endpoint so only admins can access it.
    // Assuming you have method security enabled or handle it in WebSecurityConfig.
    // For simplicity, we just check the role if needed, or use @PreAuthorize
    @GetMapping("/stats")
    public ResponseEntity<?> getOverallStats() {
        List<User> users = userRepository.findAll();
        
        long totalUsers = users.size();
        double totalCarbonEmitted = 0;
        
        for (User user : users) {
            totalCarbonEmitted += footprintService.getTotalFootprint(user.getId());
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("totalCarbonEmitted", totalCarbonEmitted);
        stats.put("averageCarbonPerUser", totalUsers > 0 ? totalCarbonEmitted / totalUsers : 0);
        stats.put("topContributors", users.stream().limit(5).collect(java.util.stream.Collectors.toList()));
        stats.put("monthlyStatistics", java.util.Arrays.asList(100, 200, 150, 300));

        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
