package com.ecotrack.repository;

import com.ecotrack.model.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WasteRepository extends JpaRepository<Waste, Long> {
    List<Waste> findByUserId(Long userId);
}
