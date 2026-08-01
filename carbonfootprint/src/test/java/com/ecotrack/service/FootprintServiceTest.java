package com.ecotrack.service;

import com.ecotrack.model.*;
import com.ecotrack.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FootprintServiceTest {

    @Mock
    private TransportationRepository transportRepo;

    @Mock
    private ElectricityRepository electricityRepo;
    
    @Mock
    private FoodRepository foodRepo;

    @Mock
    private WasteRepository wasteRepo;

    @InjectMocks
    private FootprintService footprintService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTransportationCar() {
        User user = new User();
        user.setId(1L);

        Transportation t = new Transportation();
        t.setCarbonEmitted(10 * 0.192); // 10km car

        when(transportRepo.save(any(Transportation.class))).thenReturn(t);

        Transportation result = footprintService.addTransportation(user, "car", 10.0, LocalDate.now());
        assertEquals(1.92, result.getCarbonEmitted(), 0.001);
    }
    
    @Test
    void testGetTotalFootprint() {
        User user = new User();
        user.setId(1L);
        
        Transportation t = new Transportation();
        t.setCarbonEmitted(5.0);
        when(transportRepo.findByUserId(1L)).thenReturn(Collections.singletonList(t));
        
        Electricity e = new Electricity();
        e.setCarbonEmitted(10.0);
        when(electricityRepo.findByUserId(1L)).thenReturn(Collections.singletonList(e));
        
        when(foodRepo.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(wasteRepo.findByUserId(1L)).thenReturn(Collections.emptyList());
        
        double total = footprintService.getTotalFootprint(1L);
        assertEquals(15.0, total, 0.001);
    }
}
