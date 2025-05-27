package com.paymentprocessor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paymentprocessor.dto.PaymentRequestDto;
import com.paymentprocessor.producer.KafkaPaymentProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final KafkaPaymentProducer producer;

    @PostMapping
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequestDto request) throws JsonProcessingException {
        producer.sendPayment(request);
        return ResponseEntity.ok("Payment initiated");
    }
}
