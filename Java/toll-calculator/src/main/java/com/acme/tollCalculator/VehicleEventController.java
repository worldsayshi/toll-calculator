package com.acme.tollCalculator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleEventController {

    @Autowired
    private VehicleEventRepository vehicleEventRepository;

    @Autowired
    private TollCalculator tollCalculator;

    @GetMapping("/vehicleEvents")
    public List<VehicleEvent> getAllVehicleEvents() {
        return vehicleEventRepository.findAll();
    }

    @PostMapping("/vehicleEvents")
    public VehicleEvent createVehicleEvent(@RequestBody VehicleEvent vehicleEvent) {
        return vehicleEventRepository.save(vehicleEvent);
    }


    @GetMapping("/calculateToll")
    public int calculateMonthlyToll(
            @RequestParam String registrationNumber,
            @RequestParam TollData.Vehicle vehicle,
            @RequestParam int year,
            @RequestParam int month) {

        // Define the start and end of the month
        LocalDateTime startOfMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        vehicleEventRepository.findByRegistrationNumberAndEventDateBetween(registrationNumber, startOfMonth, endOfMonth);

        // Fetch events for the registration number within the specified date range
        List<VehicleEvent> events = vehicleEventRepository.findByRegistrationNumberAndEventDateBetween(registrationNumber, startOfMonth, endOfMonth);

        // Convert LocalDateTime to Date
        Date[] dates = events.stream()
                .map(event -> Date.from(event.getEventDate().atZone(java.time.ZoneId.systemDefault()).toInstant()))
                .toArray(Date[]::new);

        // Calculate and return the toll fee
        return tollCalculator.getTollFee(vehicle, dates);
    }

    }

