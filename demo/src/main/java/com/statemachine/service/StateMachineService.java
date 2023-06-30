package com.statemachine.service;

import com.statemachine.states.MyActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
public class StateMachineService {


    private final StateMachine<MyActions.MyState, MyActions.MyEvent> stateMachine;

    @Autowired
    public StateMachineService(StateMachine<MyActions.MyState, MyActions.MyEvent> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public void performStateTransition() {
        stateMachine.start();
        stateMachine.sendEvent(MyActions.MyEvent.TRIGGER_EVENT);
    }
}
