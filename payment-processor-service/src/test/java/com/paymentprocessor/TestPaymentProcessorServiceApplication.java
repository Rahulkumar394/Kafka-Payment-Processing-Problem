package com.paymentprocessor;

import org.springframework.boot.SpringApplication;

public class TestPaymentProcessorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentProcessorServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
