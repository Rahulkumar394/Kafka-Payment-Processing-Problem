package com.paymentprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableKafka
public class PaymentProcessorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessorServiceApplication.class, args);
	}
	
	@Bean(name = "kafkaTransactionManager")
	public KafkaTransactionManager<String, String> kafkaTransactionManager(
	        ProducerFactory<String, String> producerFactory) {
	    return new KafkaTransactionManager<>(producerFactory);
	}

	@Bean  
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    return new JpaTransactionManager(emf);
	}


}
