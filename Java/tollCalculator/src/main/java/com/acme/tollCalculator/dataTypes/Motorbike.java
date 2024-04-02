package com.acme.tollCalculator.dataTypes;


public class Motorbike implements Vehicle {
  public static String typeName = "Motorbike";
  @Override
  public String getType() {
    return typeName;
  }
}
