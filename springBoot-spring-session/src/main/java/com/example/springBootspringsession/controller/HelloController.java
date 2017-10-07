package com.example.springBootspringsession.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzhenhui on 2017/9/5.
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String printHello(){
        return "hello Spring boot";
    }

}
