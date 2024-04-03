package com.acme.tollCalculator.dataTypes;


public class Car_ implements Vehicle {
  public static String typeName = "Car";
  @Override
  public String getType() {
    return typeName;
  }
}
