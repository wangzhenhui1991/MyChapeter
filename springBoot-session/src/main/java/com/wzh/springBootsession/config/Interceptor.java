package com.wzh.springBootsession.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Interceptor implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(Interceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        if(request.getSession()!=null){
            String sessionSource = request.isRequestedSessionIdFromCookie()?"cookie":(request.isRequestedSessionIdFromURL()?"url":"unknon");
            boolean isValidSessionId = request.isRequestedSessionIdValid();

            logger.info("'\nId:{}-from {} - isValid:{}\nCreateTime:{}\nLastAccessTime:{}"
                    ,request.getSession().getId()
                    ,sessionSource
                    ,isValidSessionId
                    ,dateFormat.format(new Date(request.getSession().getCreationTime()))
                    //lastAccessTime 上次访问时间
                    ,dateFormat.format(new Date(request.getSession().getLastAccessedTime())));
            //最大间隔懒惰性-是指客户端两次访问之间最大失效时间
//            request.getSession().setMaxInactiveInterval(600);
//            logger.info("aaa");

            while(request.getSession().getAttributeNames().hasMoreElements()){
                String sessionKey = request.getSession().getAttributeNames().nextElement();
                logger.info("\n{},{}",sessionKey,request.getSession().getAttribute(sessionKey));
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
