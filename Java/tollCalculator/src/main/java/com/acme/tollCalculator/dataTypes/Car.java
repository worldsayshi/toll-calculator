package com.acme.tollCalculator.dataTypes;


public class Car implements Vehicle {
  public static String typeName = "Motorbike";
  @Override
  public String getType() {
    return typeName;
  }
}
