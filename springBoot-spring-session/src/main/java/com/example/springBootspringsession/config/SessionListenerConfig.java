package com.example.springBootspringsession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;



/**
 * Session 事件监听
 * 在Spirng-Session监听失效
 */
@Configuration
public class SessionListenerConfig {

    @Bean                           // bean for http session listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {               // This method will be called when session created
                System.out.println("Session Created with session id+" + se.getSession().getId());
            }
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {         // This method will be automatically called when session destroyed

                System.out.println("Session Destroyed, Session id:" + se.getSession().getId()+"\n"+(System.currentTimeMillis()-se.getSession().getCreationTime()));
            }
        };
    }
}
