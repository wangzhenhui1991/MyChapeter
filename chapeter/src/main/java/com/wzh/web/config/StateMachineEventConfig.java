package com.wzh.web.config;

import com.wzh.web.enumerate.State;
import com.wzh.web.enumerate.StateMachineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * Created by wangzhenhui on 2017/5/9.
 */
@WithStateMachine
public class StateMachineEventConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @OnTransition(target = "UNPAID")
    public void create(StateContext<State, StateMachineEvent> context) {

        logger.info("订单创建，待支付;context = {}",context.getEvent());
    }
    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay(StateContext<State, StateMachineEvent> context) {
        logger.info("用户完成支付，待收货{}",context.getMessageHeader("header"));
    }
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        logger.info("用户已收货，订单完成");
    }
}
