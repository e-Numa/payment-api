package com.enuma.paymentapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PaymentEntityApiApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Application context loaded successfully");
	}
}
