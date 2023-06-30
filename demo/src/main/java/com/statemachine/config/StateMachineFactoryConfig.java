package com.statemachine.config;

import com.statemachine.states.MyActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@Configuration
public class StateMachineFactoryConfig {

    @Bean
    public StateMachineFactory<MyActions.MyState, MyActions.MyEvent> stateMachineFactory() {
        // Define and configure your StateMachineFactory implementation here
        return new MyStateMachineFactory();
    }
}

class MyStateMachineFactory implements StateMachineFactory<MyActions.MyState, MyActions.MyEvent> {

    private Logger logger = LoggerFactory.getLogger(MyStateMachineFactory.class);
    @Override
    public StateMachine<MyActions.MyState, MyActions.MyEvent> getStateMachine() {
        this.logger.info("Override method 1 !!");
        return null;
    }

    @Override
    public StateMachine<MyActions.MyState, MyActions.MyEvent> getStateMachine(String s) {
        this.logger.info("Override method 2 !!");
        return null;
    }

    @Override
    public StateMachine<MyActions.MyState, MyActions.MyEvent> getStateMachine(UUID uuid) {
        this.logger.info("Override method 3 !!");
        return null;
    }
}
