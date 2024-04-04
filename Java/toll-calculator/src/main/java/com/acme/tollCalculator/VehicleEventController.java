package com.acme.tollCalculator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicleEvents")
public class VehicleEventController {

    @Autowired
    private VehicleEventRepository vehicleEventRepository;

    @GetMapping
    public List<VehicleEvent> getAllVehicleEvents() {
        return vehicleEventRepository.findAll();
    }

    @PostMapping
    public VehicleEvent createVehicleEvent(@RequestBody VehicleEvent vehicleEvent) {
        return vehicleEventRepository.save(vehicleEvent);
    }
}

