package com.learnspring.ssm.controller;

import com.learnspring.ssm.domain.Payment;
import com.learnspring.ssm.domain.PaymentEvent;
import com.learnspring.ssm.domain.PaymentState;
import com.learnspring.ssm.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/sm")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<?> testPayment(@RequestBody Payment payment) {

        String result = "";
        Payment savedPayment = this.paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> preAuthSM = this.paymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId().equals(PaymentState.PRE_AUTH)) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> authSM = this.paymentService.authorizePayment(savedPayment.getId());
            System.out.println("Result of Auth: " + authSM.getState().getId());
            result = String.valueOf(authSM.getState().getId());
        } else {
            result = "Payment failed pre-auth";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", result));
    }
}
