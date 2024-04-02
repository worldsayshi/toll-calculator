package com.acme.tollCalculator.dataTypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = VehicleDeserializer.class)
public interface Vehicle {

  public String getType();
}
