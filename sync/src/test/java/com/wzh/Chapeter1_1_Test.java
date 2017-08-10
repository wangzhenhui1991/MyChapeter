package com.wzh;

import org.junit.Test;

/**
 * 定义并启动一个线程:
 * 1. 实现Runnable接口
 * 2. 继承Thread类
 * Created by wangzhenhui on 2017/8/10.
 */
public class Chapeter1_1_Test {
    public static void main(String args[]) {
        //继承Thread
        Thread ticketService= new HelloThread();
        ticketService.start();
        ticketService.start();
        ticketService.start();
    }

}
/**
 * 推荐
 * 因为Runnable对象可以继承于其他类（Java只支持单继承，当一个类继承与Thread类后，就无法继承与其他类）
 */
 class HelloRunnable implements Runnable {
    private int restTicket = 10;
    public void run() {
        while (restTicket > 0) {
            restTicket--;
            System.out.println(Thread.currentThread().getName() + "：Hello from a thread implements Runnable!" + "\nrestTicket=" + restTicket);
        }
    }

    public static void main(String args[]) {
        //实现Runnable
        (new Thread(new HelloRunnable())).start();
        (new Thread(new HelloRunnable())).start();
        (new Thread(new HelloRunnable())).start();
    }
}

/**
 *
 */
class HelloThread extends Thread {
    private int restTicket = 10;
    public void run() {
        while (restTicket > 0) {
            restTicket--;
            System.out.println(Thread.currentThread().getName()+"：Hello from a thread extends Thread!"+"\nrestTicket="+restTicket);
        }
    }



}