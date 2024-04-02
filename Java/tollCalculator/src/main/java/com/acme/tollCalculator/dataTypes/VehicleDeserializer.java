package com.acme.tollCalculator.dataTypes;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class VehicleDeserializer extends JsonDeserializer<Vehicle> {

    private HashMap<String, Class<? extends Vehicle>> vehicleTypeMap;

    public VehicleDeserializer() {
        init();
    }

    private void init() {
		var vehicleTM = new HashMap<String, Class<? extends Vehicle>>();
		vehicleTM.put("Motorbike", Motorbike.class);
		vehicleTM.put("Car", Car.class);
		this.vehicleTypeMap = vehicleTM;
    }

    @Override
    public Vehicle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var text = p.getText();
        var vehicle = getVehicleFromString(text);
        return vehicle;
    }

    public String[] getTypeNames() {
        return vehicleTypeMap.keySet().toArray(new String[0]);
    }

    private Vehicle getVehicleFromString(String vehicleType) {
        if (vehicleType.toLowerCase().contains(Motorbike.typeName)) {
            return new Motorbike();
        }
        if (vehicleType.contains(Car.typeName)) {
            return new Car();
        }
        return null;
    }

}
