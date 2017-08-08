package com.wzh.web;

import com.wzh.web.dto.User;
import com.wzh.web.enumerate.State;
import com.wzh.web.enumerate.StateMachineEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

/**
 * Created by wangzhenhui on 2017/5/9.
 */
public class StateMachineTest extends BaseTest {

    @Autowired
    private StateMachine<State, StateMachineEvent> stateMachine;

    @org.junit.Test
    public void StateMachineTest(){
        stateMachine.start();
        Message<StateMachineEvent> message = MessageBuilder
                .withPayload(StateMachineEvent.PAY)
                .setHeader("header",new User(11,"wzh",26))
                .build();

        stateMachine.sendEvent(message);
        stateMachine.sendEvent(StateMachineEvent.RECEIVE);
    }

}
