package com.ecotrack.controller;

import com.ecotrack.model.User;
import com.ecotrack.repository.UserRepository;
import com.ecotrack.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId()).orElse(null);
    }

    @GetMapping
    public ResponseEntity<?> getProfile() {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody Map<String, String> payload) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.notFound().build();
        user.setAvatarUrl(payload.get("avatarUrl"));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/darkmode")
    public ResponseEntity<?> toggleDarkMode(@RequestBody Map<String, Boolean> payload) {
        User user = getCurrentUser();
        if (user == null) return ResponseEntity.notFound().build();
        user.setDarkModeEnabled(payload.get("enabled"));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
