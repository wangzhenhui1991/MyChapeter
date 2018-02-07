package com.wzh.springboot.session.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * Session 事件监听
 */
public class SessionListener implements HttpSessionListener {
    static Logger logger = LoggerFactory.getLogger(SessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("\nsession:{}-{}",se.getSession().getId(),se.getSession().isNew()?"is new!":"has been created!");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        long sessionPersistTime =  new Date().getTime()-se.getSession().getCreationTime();
        long lastAccessTime = new Date().getTime()-se.getSession().getLastAccessedTime();
        logger.info("\nsession:{} has bean destroyeed!\npersistTime:{}(s)\nlastAccessTime:{}(s)",se.getSession().getId(),sessionPersistTime/1000,lastAccessTime/1000);
    }
}
