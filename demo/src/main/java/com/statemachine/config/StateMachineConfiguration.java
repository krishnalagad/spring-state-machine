package com.statemachine.config;

import com.statemachine.states.MyActions;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<MyActions.MyState, MyActions.MyEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<MyActions.MyState, MyActions.MyEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true); // Configure general aspects of the state machine
    }

    @Override
    public void configure(StateMachineStateConfigurer<MyActions.MyState, MyActions.MyEvent> states) throws Exception {
        states
                .withStates()
                .initial(MyActions.MyState.INITIAL_STATE) // Set the initial state
                .states(EnumSet.allOf(MyActions.MyState.class)); // Define all the states
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MyActions.MyState, MyActions.MyEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(MyActions.MyState.INITIAL_STATE).target(MyActions.MyState.NEXT_STATE).event(MyActions.MyEvent.TRIGGER_EVENT); //
        // Define the
        // transitions between states based on events
    }
}
