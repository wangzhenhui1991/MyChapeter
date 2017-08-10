package com.wzh.controller;

import com.wzh.domian.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangzhenhui on 2017/8/10.
 */
@RestController
public class ConcurrentController {


    private final static Logger logger           = LoggerFactory.getLogger(ConcurrentController.class);

    private static int                corePoolSize     = 50;
    private static int                maxPoolSize      = 200;
    private static int                keepAliveSeconds = 60;
    private static int                queueCapacity    = 5000000;
    private static ThreadPoolExecutor threadPoolExecutor;

    private static boolean            isRun            = true;


    @Autowired
    SyncService syncService;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue(queueCapacity));

    }


    @RequestMapping(value = "/performance/sync/stop", method = { RequestMethod.GET })
    public String stop() {
        isRun = false;
        syncService.printResult();
        return "OK";
    }

    /**
     * 开始轰炸
     * @return
     */
    @RequestMapping(value = "/performance/sync/start", method = { RequestMethod.GET })
    public String start(){
        isRun = true;
        for (int i = 0; i < 10 ; i++) {
            threadPoolExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        while (isRun) {
                            syncService.output("abcdefghijklmnopqrst");
                        }
                    } catch (Throwable e) {
                        logger.error("{}", e.getMessage(), e);
                    }
                }
            });
        }
        return "OK";
    }
}
