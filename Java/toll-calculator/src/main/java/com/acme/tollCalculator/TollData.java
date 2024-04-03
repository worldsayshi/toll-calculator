package com.acme.tollCalculator;

import com.acme.tollCalculator.TollCalculator.Vehicle;

import java.util.*;

public class TollData {
    public static Set<Vehicle> tollFreeVehicles = Set.of(
        Vehicle.MOTORBIKE,
        Vehicle.TRACTOR,
        Vehicle.EMERGENCY,
        Vehicle.DIPLOMAT,
        Vehicle.FOREIGN,
        Vehicle.MILITARY
    );
}
