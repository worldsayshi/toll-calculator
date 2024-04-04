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


    // 06:00 - 06:29 -> 8
    // 06:30 - 06:59 -> 13
    // 07:00 - 07:59 -> 18
    // 08:00 - 08:29 -> 13
    // 08:30 - 14:59 -> 8
    // 15:00 - 16:59 -> 18
    // 17:00 - 17:59 -> 13
    // 18:00 - 18:29 -> 8
    // else -> 0
    @Test
    public void testFeesVaryByTimeOfDay() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;
        
        // Example: specify times that should incur different fees
        Date lowFeeTime = createTime(10, 0); // Time that should incur a low fee (8 SEK)
        Date highFeeTime = createTime(7, 14); // Rush-hour time expected to incur high fee (18 SEK)

        assertEquals(8, calculator.getTollFee(vehicle, lowFeeTime), "Fee not 8 for low-fee time");
        assertEquals(18, calculator.getTollFee(vehicle, highFeeTime), "Fee not 18 for high-fee time");
        assertTrue(calculator.getTollFee(vehicle, lowFeeTime) >= 8 && calculator.getTollFee(vehicle, lowFeeTime) <= 18, "Fee outside expected range for low-fee time");
        assertTrue(calculator.getTollFee(vehicle, highFeeTime) == 18, "Expected high fee during rush hour not applied");
    }

    // The legacy implementation of getTollFee for a single vehicle
    public int getTollFee(final Date date, Vehicle vehicle) {
        if(new TollCalculator().isTollFreeDate(date) || TollCalculator.isTollFreeVehicle(vehicle)) return 0;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }


    @Test
    public void testMaximumFeePerDay() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;
        
        // Example: specify multiple times throughout a day that would exceed the maximum daily fee
        Date[] dates = {createTime(7, 0), createTime(8, 30), createTime(9, 30), createTime(15, 0), createTime(17, 0)};
        
        assertTrue(calculator.getTollFee(vehicle, dates) <= 60, "Total fee for one day exceeded the maximum limit of 60 SEK");
    }

    @Test
    public void testChargeOncePerHour() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;
        
        // Example: times within the same hour, expect to be charged for the highest fee only
        Date withinSameHour1 = createTime(8, 0);
        Date withinSameHour2 = createTime(8, 15); // Assuming this should incur a higher fee
        
        int expectedFee = Math.max(calculator.getTollFee(vehicle, withinSameHour1), calculator.getTollFee(vehicle, withinSameHour2));
        assertEquals(expectedFee, calculator.getTollFee(vehicle, withinSameHour1, withinSameHour2), "Did not charge the highest fee within the same hour");
    }

    @Test
    public void testFeeFreeVehicleTypes() {
        TollCalculator calculator = new TollCalculator();
        Vehicle feeFreeVehicle = Vehicle.EMERGENCY;
        
        Date tenOClock = createTime(10, 0);
        
        assertEquals(0, calculator.getTollFee(feeFreeVehicle, tenOClock), "Emergency vehicle was incorrectly charged");
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

    // Utility methods to create Date instances for testing.
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
        // return Date.from(Instant.parse(String.format("%04d-%02d-%02dT10:00:00Z", year, month, day)));
    }
}

