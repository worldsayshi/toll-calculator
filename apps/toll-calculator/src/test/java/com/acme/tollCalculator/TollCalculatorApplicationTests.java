package com.acme.tollCalculator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class TollCalculatorApplicationTests {

	// This is disabled because it caused Spring Data JPA to try to set up a database connection when testing, which is not currently desired
	@Disabled
	@Test
	void contextLoads() {
	}

}
