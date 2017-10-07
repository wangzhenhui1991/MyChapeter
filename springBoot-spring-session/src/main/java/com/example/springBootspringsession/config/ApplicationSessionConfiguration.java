package com.example.springBootspringsession.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import javax.servlet.http.HttpSessionListener;

@Configuration
public class ApplicationSessionConfiguration {

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener()
    {
        return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
    }

}
