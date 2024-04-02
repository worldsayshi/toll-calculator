package com.acme.tollCalculator;

import org.springframework.format.annotation.DateTimeFormat;
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
        TollCalculator tollCalculator
        , VehicleDeserializer vehicleDeserializer
    ) {
        this.tollCalculator = tollCalculator;
        this.vehicleDeserializer = vehicleDeserializer;
    }

    @GetMapping("/get-toll-fee")
    public int getTollFee(
        @RequestParam(value = "vehicle") String vehicle,
        @RequestParam(value = "dates") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date[] dates
    ) {
        var v = vehicleDeserializer.getVehicleFromString(vehicle);
        return tollCalculator.getTollFee(v, dates);
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