package com.example.springBootspringsession.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangzhenhui on 2017/9/5.
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String printHello(HttpServletRequest request){
        Object value = request.getSession().getAttribute("date");
        return "hello Spring boot"+value.toString();
    }

}
