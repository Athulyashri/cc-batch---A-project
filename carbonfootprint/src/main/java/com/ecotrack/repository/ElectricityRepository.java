package com.ecotrack.repository;

import com.ecotrack.model.Electricity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ElectricityRepository extends JpaRepository<Electricity, Long> {
    List<Electricity> findByUserId(Long userId);
}
