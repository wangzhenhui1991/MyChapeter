package com.wzh.web.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wangzhenhui on 2017/5/7.
 */
public class RandomProperties {
    @Value("${com.wzh.web.chapeter.random.value}")
    public String randomValue;
    @Value("${com.wzh.web.chapeter.random.long.number}")
    public Long randomLong;

    @Value("${com.wzh.web.chapeter.random.number}")
    public Integer randomInt;

    @Value("${com.wzh.web.chapeter.random.interval.number}")
    public Integer randomIntervalNumber;
}
