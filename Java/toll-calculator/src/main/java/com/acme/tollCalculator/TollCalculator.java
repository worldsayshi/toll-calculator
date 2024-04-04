package com.acme.tollCalculator;
import com.acme.tollCalculator.TollData.Vehicle;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import de.focus_shift.jollyday.core.HolidayManager;
import de.focus_shift.jollyday.core.ManagerParameters;
import static de.focus_shift.jollyday.core.HolidayCalendar.SWEDEN;
import java.time.LocalDate;

public class TollCalculator {

  final HolidayManager holidayManager;
  public TollCalculator() {
    holidayManager = HolidayManager.getInstance(ManagerParameters.create(SWEDEN));
  }
  /**
   * Calculate the total toll fee for one day
   *
   * @param vehicle - the vehicle
   * @param dates   - date and time of all passes on one day
   * @return - the total toll fee for that day
   */
  public int getTollFee(Vehicle vehicle, Date... dates) {
    Date intervalStart = dates[0];
    int totalFee = 0;
    for (Date date : dates) {
      int nextFee = getTollFee(date, vehicle);
      int tempFee = getTollFee(intervalStart, vehicle);

      TimeUnit timeUnit = TimeUnit.MINUTES;
      long diffInMillies = date.getTime() - intervalStart.getTime();
      long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

      if (minutes <= 60) {
        if (totalFee > 0) {totalFee -= tempFee;}
        if (nextFee >= tempFee) {tempFee = nextFee;}
        totalFee += tempFee;
      } else {
        totalFee += nextFee;
      }
    }
    if (totalFee > 60) totalFee = 60;
    return totalFee;
  }

  public static boolean isTollFreeVehicle(Vehicle vehicle) {
    if(vehicle == null) return false;
    return TollData.tollFreeVehicles.contains(vehicle);
  }

  public int getTollFee(final Date date, Vehicle vehicle) {
    if(isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(date);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    
    if (hour == 6 && minute >= 0 && minute <= 29) return 8;
    else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
    else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
    else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
    // hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59
    else if (hour == 8 && minute >= 30
            || hour >= 9 && hour <= 13
            || hour == 14 && minute >= 0 && minute <= 59) return 8; // Isn't there rush hour around lunch as well???
    else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
    else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
    else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
    else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
    else return 0;
  }

  public Boolean isTollFreeDate(Date date) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) return true;

    return holidayManager.isHoliday(LocalDate.of(year, month, day));
    // This does not work at all!!
//    if (year == 2013) {
//      if (month == Calendar.JANUARY && day == 1 ||
//          month == Calendar.MARCH && (day == 28 || day == 29) ||
//          month == Calendar.APRIL && (day == 1 || day == 30) ||
//          month == Calendar.MAY && (day == 1 || day == 8 || day == 9) ||
//          month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) ||
//          month == Calendar.JULY ||
//          month == Calendar.NOVEMBER && day == 1 ||
//          month == Calendar.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31)) {
//        return true;
//      }
//    }
//    return false;
  }

}

