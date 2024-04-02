package com.acme.tollCalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tollCalculator.dataTypes.Vehicle;
import com.acme.tollCalculator.dataTypes.VehicleDeserializer;

import java.util.*;
//import java.util.concurrent.*;

@RestController
public class TollCalculatorController {

    private final TollCalculator tollCalculator;
    private final VehicleDeserializer vehicleDeserializer;

    public TollCalculatorController(
        TollCalculator tollCalculator,
        VehicleDeserializer vehicleDeserializer
    ) {
        this.tollCalculator = tollCalculator;
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

    @GetMapping("/get-toll-fee")
    public int getTollFee(@RequestParam Vehicle vehicle, @RequestParam(value = "date[]") Date[] dates) {
        return tollCalculator.getTollFee(vehicle, dates);
    }

}