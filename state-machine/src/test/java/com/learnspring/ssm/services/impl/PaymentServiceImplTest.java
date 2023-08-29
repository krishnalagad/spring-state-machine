package com.learnspring.ssm.services.impl;

import com.learnspring.ssm.domain.Payment;
import com.learnspring.ssm.domain.PaymentEvent;
import com.learnspring.ssm.domain.PaymentState;
import com.learnspring.ssm.repository.PaymentRepository;
import com.learnspring.ssm.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
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

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getState());

        StateMachine<PaymentState, PaymentEvent> sm = this.paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment =
                this.paymentRepository.findById(savedPayment.getId()).orElseThrow(() -> new RuntimeException(String
                        .format("Payment not found with ID %s", savedPayment.getId().toString())));

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(sm.getState().getId());

        System.out.println(preAuthedPayment);
    }

    @Transactional
    @RepeatedTest(10)
    void testAuth() {
        Payment savedPayment = this.paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthSM = this.paymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId().equals(PaymentState.PRE_AUTH)) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> authSM = this.paymentService
                    .authorizePayment(savedPayment.getId());

            System.out.println("Result of Auth: " + authSM.getState().getId());
        } else {
            System.out.println("Payment failed pre-auth");
        }
    }
}