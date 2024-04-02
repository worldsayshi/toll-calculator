package com.acme.tollCalculator;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.acme.tollCalculator.dataTypes.VehicleDeserializer;

@SpringBootApplication
public class TollCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TollCalculatorApplication.class, args);
	}

	@Bean
	public TollCalculator tollCalculator() {
		return new TollCalculator();
	}

	@Bean
	public VehicleDeserializer vehicleTypeRegistry() {
		return new VehicleDeserializer();
	}
}
