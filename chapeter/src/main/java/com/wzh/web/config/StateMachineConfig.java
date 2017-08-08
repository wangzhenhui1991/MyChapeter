package com.wzh.web.config;

import com.wzh.web.enumerate.State;
import com.wzh.web.enumerate.StateMachineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * Created by wangzhenhui on 2017/5/9.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, StateMachineEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void configure(StateMachineStateConfigurer<State, StateMachineEvent> states)
            throws Exception {
        states
                .withStates()
                .initial(State.UNPAID)
                .states(EnumSet.allOf(State.class));
    }
    @Override
    public void configure(StateMachineTransitionConfigurer<State, StateMachineEvent> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(State.UNPAID).target(State.WAITING_FOR_RECEIVE)
                .event(StateMachineEvent.PAY)
                .and()
                .withExternal()
                .source(State.WAITING_FOR_RECEIVE).target(State.DONE)
                .event(StateMachineEvent.RECEIVE);
    }
}
