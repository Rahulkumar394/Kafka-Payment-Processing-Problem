package com.paymentprocessor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymentprocessor.dto.PaymentRequestDto;
import com.paymentprocessor.entity.Payment;
import com.paymentprocessor.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

    @Transactional
    public Payment process(PaymentRequestDto request) {
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status("CONFIRMED")
                .build();

        return paymentRepository.save(payment);
    }
}
