package com.acme.tollCalculator;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.tollCalculator.TollData.Vehicle;

import java.util.*;
@RestController
public class TollCalculatorController {

    private final TollCalculator tollCalculator;

    public TollCalculatorController(
        TollCalculator tollCalculator
    ) {
        this.tollCalculator = tollCalculator;
    }

    @GetMapping("/get-toll-fee")
    public int getTollFee(
        @RequestParam(value = "vehicle") Vehicle vehicle,
        @RequestParam(value = "dates") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date[] dates
    ) {
        return tollCalculator.getTollFee(vehicle, dates);
    }

}