package com.learnspring.ssm.services.impl;

import com.learnspring.ssm.domain.Payment;
import com.learnspring.ssm.repository.PaymentRepository;
import com.learnspring.ssm.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    void setUp() {
        this.payment = Payment.builder().amount(new BigDecimal("31.34")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = this.paymentService.newPayment(this.payment);

        this.paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment =
                this.paymentRepository.findById(savedPayment.getId()).orElseThrow(() -> new RuntimeException(String
                        .format("Payment not found with ID %s", savedPayment.getId().toString())));
        System.out.println(preAuthedPayment);
    }
}