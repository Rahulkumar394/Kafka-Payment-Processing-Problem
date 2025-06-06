package com.paymentprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymentprocessor.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
