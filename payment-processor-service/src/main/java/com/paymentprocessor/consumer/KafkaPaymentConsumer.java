package com.paymentprocessor.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentprocessor.dto.PaymentRequestDto;
import com.paymentprocessor.service.PaymentService;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPaymentConsumer {

    private final PaymentService paymentService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "payments.initiated", groupId = "payment-group")
    @Transactional(transactionManager = "transactionManager")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            PaymentRequestDto request = objectMapper.readValue(message, PaymentRequestDto.class);
            paymentService.process(request);
            kafkaTemplate.send("payments.processed", request.getUserId(), "Payment processed");
            log.info("payments.processed", request.getUserId(), "Payment processed.");
        } catch (Exception e) {
            log.error("Failed to process message", e);
            throw new RuntimeException("Consumer processing failed");
        }
    }

    @PreDestroy
    public void onShutdown() {
        log.info("Gracefully shutting down consumer...");
    }
}
