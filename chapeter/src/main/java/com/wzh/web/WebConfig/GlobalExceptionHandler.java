package com.wzh.web.WebConfig;

import com.wzh.web.Exception.MyException;
import com.wzh.web.dto.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangzhenhui on 2017/5/7.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    //@ExceptionHandler用来定义函数针对的异常类型
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }


    //捕获自定义异常MyException,并且通过@ResponseBody 返回json格式数据
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<Object> myErrorHandler(HttpServletRequest req, MyException e){
        ErrorInfo<Object> r = new ErrorInfo<>();
        r.setCode(e.getErrCode());
        r.setMessage(e.getErrMsg());
        r.setData(e.getData());
        return r;
    }
}
