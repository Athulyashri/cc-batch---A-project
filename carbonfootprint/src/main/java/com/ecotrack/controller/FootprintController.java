package com.ecotrack.controller;

import com.ecotrack.model.*;
import com.ecotrack.repository.*;
import com.ecotrack.security.UserDetailsImpl;
import com.ecotrack.service.FootprintService;
import com.ecotrack.service.PdfService;
import com.ecotrack.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/footprint")
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow();
    }

    @PostMapping("/transport")
    public ResponseEntity<?> addTransport(Authentication auth, @RequestBody Transportation request) {
        Transportation t = footprintService.addTransportation(getCurrentUser(auth), request.getType(), request.getDistance(), LocalDate.now());
        return ResponseEntity.ok(t);
    }

    @PostMapping("/electricity")
    public ResponseEntity<?> addElectricity(Authentication auth, @RequestBody Electricity request) {
        Electricity e = footprintService.addElectricity(getCurrentUser(auth), request.getKwhUsed(), LocalDate.now());
        return ResponseEntity.ok(e);
    }

    @PostMapping("/food")
    public ResponseEntity<?> addFood(Authentication auth, @RequestBody Food request) {
        Food f = footprintService.addFood(getCurrentUser(auth), request.getDietType(), LocalDate.now());
        return ResponseEntity.ok(f);
    }

    @PostMapping("/waste")
    public ResponseEntity<?> addWaste(Authentication auth, @RequestBody Waste request) {
        Waste w = footprintService.addWaste(getCurrentUser(auth), request.getWasteType(), request.getWeightKg(), LocalDate.now());
        return ResponseEntity.ok(w);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(Authentication auth) {
        User user = getCurrentUser(auth);
        double total = footprintService.getTotalFootprint(user.getId());
        List<String> recommendations = recommendationService.getRecommendations(user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalFootprint", total);
        response.put("recommendations", recommendations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<InputStreamResource> generatePdfReport(Authentication auth) {
        User user = getCurrentUser(auth);
        ByteArrayInputStream bis = pdfService.generateFootprintReport(user.getId(), user.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=footprint_report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
