package com.wzh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzhenhui on 2017/10/23.
 */
@RestController
public class HelloContorller {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloContorller.class);

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "test";
    }

    /**
     * 获取
     */
    @RequestMapping(value = "/account-center/query/card",method = RequestMethod.GET)
    public void queryCardInfo(){
        try{

        }catch (Exception e){
            LOGGER.error("");
        }
    }
}
