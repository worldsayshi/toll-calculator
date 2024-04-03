package com.acme.tollCalculator.dataTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// @JsonDeserialize(using = VehicleDeserializer.class)
// @JsonSubTypes({ 
//   @Type(value = Car.class, name = "Car"),
//   @Type(value = Motorbike.class, name = "Motorbike")
// })
public interface Vehicle {

  public String getType();
}
