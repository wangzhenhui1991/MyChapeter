package com.example.springBootspringsession.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;

@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
@EnableRedisHttpSession
public class RedisHttpSessionConfig {


    @Autowired
    private RedisOperationsSessionRepository sessionRepository;

    @PostConstruct
    private void afterPropertiesSet(){
        sessionRepository.setDefaultMaxInactiveInterval(20);
    }

    /**
     * 开启:httpSessionEventPublisher
     * @return
     */
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }


    /**
     * We customize Spring Session’s HttpSession integration to use HTTP headers to convey the current session information instead of cookies.
     * @return
     */
//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }

//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }

    @Bean
    public CustomHttpSessionStrategy httpSessionStrategy(){
        return new CustomHttpSessionStrategy();
    }
//
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("JSESSIONID");
//        serializer.setCookiePath("/");
//        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[A-Z]+)$");
//        return serializer;
//    }

}
