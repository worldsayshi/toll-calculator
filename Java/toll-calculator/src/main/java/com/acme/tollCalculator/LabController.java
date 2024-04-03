package com.acme.tollCalculator;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tollCalculator.TollCalculator.Vehicle;

import java.util.*;
import java.util.stream.*;

/**
 * Temporary endpoints meant for experimentation.
 * TODO: Remove before going public
 */
@RestController
public class LabController {

    @GetMapping("/")
	public String index() {
		return "Greetings and hello from Spring Boot!";
	}

    @GetMapping("/vehicle-types")
    public List<String> getVehicleTypes() {
        return Stream.of(Vehicle.values())
            .map(Vehicle::getType)
            .collect(Collectors.toList());
    }

    @GetMapping("/current-date")
    public String getCurrentDate() {
        return new Date().toString();
    }

    @GetMapping("/parse-date")
    public String parseDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return date.toString();
    }
    
}
