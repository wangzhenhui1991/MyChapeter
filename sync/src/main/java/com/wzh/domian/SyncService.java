package com.wzh.domian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangzhenhui on 2017/8/10.
 */
@Component
public class SyncService {
    private final Logger  logger = LoggerFactory.getLogger(SyncService.class);
    /**
     * 时间计数器
     */
    static int            time   = 0;

    public static int[][] map    = new int[10][100];

    String                outputStr;

    private Lock          lock   = new ReentrantLock();                       // 锁对象

    public void output(String name) throws InterruptedException {
        //        lock.lock();// 得到锁
        try {
            outputStr = name;
            logger.info("线程启动:{}", Thread.currentThread().getName());
            for (int i = 0; i < outputStr.length(); i++, time++) {
                int row = (int) Thread.currentThread().getId() % 9;
                map[row][time]++;
                Thread.sleep(100);
            }
        } finally {
            //            lock.unlock();// 释放锁
        }
    }

    public void printResult() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    System.out.print("0");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println("\n");
        }
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        SyncService.time = time;
    }

    public static int[][] getMap() {
        return map;
    }

    public static void setMap(int[][] map) {
        SyncService.map = map;
    }

}
