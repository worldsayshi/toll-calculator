package com.acme.tollCalculator;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tollCalculator.dataTypes.VehicleDeserializer;

import java.util.*;

/**
 * Temporary endpoints meant for experimentation.
 * TODO: Remove before going public
 */
@RestController
public class LabController {

    private final VehicleDeserializer vehicleDeserializer;

    public LabController(
        VehicleDeserializer vehicleDeserializer
    ) {
        this.vehicleDeserializer = vehicleDeserializer;
    }

    @GetMapping("/")
	public String index() {
		return "Greetings and hello from Spring Boot!";
	}

    @GetMapping("/vehicle-types")
    public String[] getVehicleTypes() {
        return vehicleDeserializer.getTypeNames();
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
