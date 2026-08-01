package com.ecotrack.repository;

import com.ecotrack.model.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByUserId(Long userId);
}
