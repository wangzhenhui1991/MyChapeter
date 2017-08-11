package com.wzh;

/**
 * Chapeter3-同步 线程间的通信主要是通过共享域和引用相同的对象。 这种通信方式非常高效，不过可能会引发两种错误：线程干扰和内存一致性错误。
 * 防止这些错误发生的方法是同步。 Created by wangzhenhui on 2017/8/11.
 */
public class Chapeter1_3 {

    public static void main(String[] args) {
        Inventory consumer = new Inventory(10);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1;i<1000;i++){

                    consumer.increment();

                }
                System.out.println(consumer.ticket);
            }
        })).start();
        (new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1;i<1000;i++){
                    consumer.decrement();

                }
                System.out.println(consumer.ticket);
            }
        })).start();
    }

}

class Inventory {
    int ticket;
    int maxCount = 20;
    int minCount = 0;

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    Inventory(int value) {
        ticket = value;
    }

     public int increment() {
        int temp1  = ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;
         ++ticket;
         --ticket;

         ++ticket;
         int temp2 = ticket;
         System.out.println(Thread.currentThread().getName()+"add:" + ticket+(temp2-temp1));
         return ticket;
     }

    public int decrement() {
        int temp1  = ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;
        ++ticket;
        --ticket;

        --ticket;
        int temp2 = ticket;
        System.out.println(Thread.currentThread().getName()+"red:" + ticket+(temp2-temp1));
        return ticket;
    }
}
