package com.acme.tollCalculator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.acme.tollCalculator.TollData.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.time.Instant;

public class TollCalculatorTests {


    @Test
    public void testFeesVaryByTimeOfDay() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;

        assertEquals(0, calculator.getTollFee(vehicle, createTime(2, 32)));
        assertEquals(8, calculator.getTollFee(vehicle, createTime(6, 12)));
        assertEquals(13, calculator.getTollFee(vehicle, createTime(6, 47)));
        assertEquals(18, calculator.getTollFee(vehicle, createTime(7, 14)));
        assertEquals(13, calculator.getTollFee(vehicle, createTime(8, 5)));
        assertEquals(8, calculator.getTollFee(vehicle, createTime(10, 0)));
        assertEquals(13, calculator.getTollFee(vehicle, createTime(15, 1)));
        assertEquals(18, calculator.getTollFee(vehicle, createTime(15, 30)));
        assertEquals(13, calculator.getTollFee(vehicle, createTime(17, 44)));
        assertEquals(8, calculator.getTollFee(vehicle, createTime(18, 10)));
        assertEquals(0, calculator.getTollFee(vehicle, createTime(18, 34)));
    }


    @Test
    public void testMaximumFeePerDay() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;

        Date[] dates = {createTime(7, 0), createTime(8, 30), createTime(9, 30), createTime(15, 0), createTime(17, 0)};
        
        assertTrue(calculator.getTollFee(vehicle, dates) <= 60, "Total fee for one day exceeded the maximum limit of 60 SEK");
    }

    @Test
    public void testChargeOncePerHour() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;

        Date withinSameHour1 = createTime(6, 0);
        Date withinSameHour2 = createTime(6, 45);
        
        int expectedFee = Math.max(
                calculator.getTollFee(vehicle, withinSameHour1),
                calculator.getTollFee(vehicle, withinSameHour2)
        );
        assertEquals(expectedFee, calculator.getTollFee(vehicle, withinSameHour1, withinSameHour2), "Did not charge the highest fee within the same hour");
    }

    @Test
    public void testFeeFreeVehicleTypes() {
        TollCalculator calculator = new TollCalculator();
        Vehicle feeFreeVehicle = Vehicle.EMERGENCY;
        
        assertEquals(0, calculator.getTollFee(feeFreeVehicle, createTime(10, 0)), "Emergency vehicle was incorrectly charged");
    }

    @Test
    public void testWeekendsAndHolidaysAreFeeFree() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;
        
        assertEquals(0, calculator.getTollFee(vehicle, createSpecificDate(2024, 1, 6)), "Charged on a weekend");
        assertEquals(0, calculator.getTollFee(vehicle, createSpecificDate(2024, 1, 7)), "Charged on a weekend");
        assertEquals(0, calculator.getTollFee(vehicle, createSpecificDate(2023, 12, 24)), "Charged on a holiday");
        assertEquals(0, calculator.getTollFee(vehicle, createSpecificDate(2023, 6, 24)), "Charged on a holiday");
    }

    @Disabled
    @Test
    public void testMidsommaraftonIsFeeFree() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;

        assertEquals(0, calculator.getTollFee(vehicle, createSpecificDate(2023, 6, 23)), "Charged on midsommarafton!");
    }


    /* Utility methods to create Date instances for testing. */

    private Date createTime(int hour, int minute) {
        return Date.from(
                LocalDateTime.of(
                        2024,12,12,hour,minute
                ).atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    private Date createSpecificDate(int year, int month, int day) {
        return Date.from(
                LocalDateTime.of(
                        year,month,day,10,0
                ).atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}

