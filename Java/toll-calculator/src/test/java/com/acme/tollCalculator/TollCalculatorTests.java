package com.acme.tollCalculator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.acme.tollCalculator.TollData.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.time.Instant;

public class TollCalculatorTests {

    @Disabled
    @Test
    public void testFeesVaryByTimeOfDay() {
        TollCalculator calculator = new TollCalculator();
        Vehicle vehicle = Vehicle.CAR;
        
        // Example: specify times that should incur different fees
        Date lowFeeTime = createTime(10, 0); // Time that should incur a low fee (8 SEK)
        Date highFeeTime = createTime(8, 0); // Rush-hour time expected to incur high fee (18 SEK)
        
        assertTrue(calculator.getTollFee(vehicle, lowFeeTime) >= 8 && calculator.getTollFee(vehicle, lowFeeTime) <= 18, "Fee outside expected range for low-fee time");
        assertTrue(calculator.getTollFee(vehicle, highFeeTime) == 18, "Expected high fee during rush hour not applied");
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
        
        // Example: a weekend date and a holiday date
        Date weekendDate = createSpecificDate(2023, Calendar.SATURDAY, 10); // Assuming this is a Saturday
        Date holidayDate = createSpecificDate(2023, Calendar.DECEMBER, 25); // Assuming this is a holiday
        
        assertEquals(0, calculator.getTollFee(vehicle, weekendDate), "Charged on a weekend");
        assertEquals(0, calculator.getTollFee(vehicle, holidayDate), "Charged on a holiday");
    }

    // Utility methods to create Date instances for testing.
    private Date createTime(int hour, int minute) {
        return Date.from(Instant.parse(String.format("2024-12-12T%02d:%02d:20Z", hour, minute)));
    }

    private Date createSpecificDate(int year, int month, int day) {
        return Date.from(Instant.parse(String.format("%04d-%02d-%02dT10:00:00Z", year, month, day)));
    }

    // The legacy implementation of getTollFee for a single vehicle
    public int getTollFee(final Date date, Vehicle vehicle) {
        if(TollCalculator.isTollFreeDate(date) || TollCalculator.isTollFreeVehicle(vehicle)) return 0;
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
}

