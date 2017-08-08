package com.wzh.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzhenhui on 2017/5/6.
*/
@RestController
public class HelloController {

    @RequestMapping("/index")
    public String printHello(){
        return "hello Spring boot";
    }

}
