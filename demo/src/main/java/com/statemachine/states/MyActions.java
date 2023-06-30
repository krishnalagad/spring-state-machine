package com.statemachine.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class MyActions implements Action<MyActions.MyState, MyActions.MyEvent> {

    private Logger logger = LoggerFactory.getLogger(MyActions.class);

    @Override
    public void execute(StateContext<MyState, MyEvent> stateContext) {
        this.logger.info("State transition action 1 !!");
        this.logger.info(String.valueOf(stateContext.getStateMachine()));
        this.logger.info(String.valueOf(stateContext.getEvent()));
    }

    public enum MyState {
        INITIAL_STATE,
        NEXT_STATE,
        // Define other states as needed
    }

    public enum MyEvent {
        TRIGGER_EVENT,
        // Define other events as needed
    }



}
