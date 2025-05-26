package com.paymentprocessor.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentprocessor.dto.PaymentRequestDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaPaymentProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Transactional("kafkaTransactionManager")
	public void sendPayment(PaymentRequestDto request) throws JsonProcessingException {
		String message = objectMapper.writeValueAsString(request);
		kafkaTemplate.executeInTransaction(kt -> {
			kt.send("payments.initiated", request.getUserId(), message);
			return true;
		});
	}
}
