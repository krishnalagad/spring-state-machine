package com.learnspring.ssm.services.impl;

import com.learnspring.ssm.domain.Payment;
import com.learnspring.ssm.domain.PaymentEvent;
import com.learnspring.ssm.domain.PaymentState;
import com.learnspring.ssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState,
            PaymentEvent> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER,
                            -1L)))
                    .ifPresent(paymentId -> {
                        Payment payment =
                                this.paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException(String
                                        .format("Payment not found with ID %s", paymentId.toString())));
                        payment.setState(state.getId());
                        paymentRepository.save(payment);
                    });
        });
    }
}
