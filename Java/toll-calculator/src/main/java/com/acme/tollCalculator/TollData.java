package com.acme.tollCalculator;

import java.util.*;

public class TollData {

    /* Rules */

    public static Set<Vehicle> tollFreeVehicles = Set.of(
            Vehicle.MOTORBIKE,
            Vehicle.TRACTOR,
            Vehicle.EMERGENCY,
            Vehicle.DIPLOMAT,
            Vehicle.FOREIGN,
            Vehicle.MILITARY);

    public List<TollFeeRule> tollFeeRules = Arrays.asList(
            new TollFeeRule(6, 0, 8), // 06:00 - 06:29 -> 8
            new TollFeeRule(6, 30, 13), // 06:30 - 06:59 -> 13
            new TollFeeRule(7, 0, 18), // 07:00 - 07:59 -> 18
            new TollFeeRule(8, 0, 13), // 08:00 - 08:29 -> 13
            new TollFeeRule(8, 30, 8), // 08:30 - 14:59 -> 8
            new TollFeeRule(15, 0, 18), // 15:00 - 16:59 -> 18
            new TollFeeRule(17, 0, 13), // 17:00 - 17:59 -> 13
            new TollFeeRule(18, 30, 8), // 18:00 - 18:29 -> 8
            new TollFeeRule(18, 0, 0) // else -> 0
    );


    /* Data types */

    public enum Vehicle {
        CAR("Car"),
        MOTORBIKE("Motorbike"),
        TRACTOR("Tractor"),
        EMERGENCY("Emergency"),
        DIPLOMAT("Diplomat"),
        FOREIGN("Foreign"),
        MILITARY("Military");

        private final String type;

        Vehicle(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * A Toll fee rule designates a cutoff point between one point in time and
     * another.
     * The rate given in the rule is to be used until the next cutoff point.
     */
    public class TollFeeRule {
        final public int hour;
        final public int minute;
        final public int rate;

        public TollFeeRule(int hour, int minute, int rate) {
            this.hour = hour;
            this.minute = minute;
            this.rate = rate;
        }
    }
}
