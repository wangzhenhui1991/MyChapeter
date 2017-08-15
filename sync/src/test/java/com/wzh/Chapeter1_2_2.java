package com.wzh;

/**
 * [线程通信](http://wiki.jikexueyuan.com/project/java-concurrent/thread-communication.html)
 * 线程通信的目标是使线程间能够互相发送信号。另一方面，线程通信使线程能够等待其他线程的信号。
 *
 * Created by wangzhenhui on 2017/8/14.
 */
public class Chapeter1_2_2 {
}

/**
 * 1.
 * 共享变量-达到线程间通信
 * 线程间发送信号的一个简单方式是在共享对象的变量里设置信号值。
 */
class SharedVarSignal{
    protected  boolean isFiniashed = false;
    public synchronized boolean hasFinishied(){
        return this.isFiniashed;
    }
    public synchronized void setFiniashed(boolean isFiniashed){
        this.isFiniashed = isFiniashed;
    }
}

class ThreadA implements Runnable{
    SharedVarSignal signal;
    ThreadA( SharedVarSignal signal){
        this.signal = signal;
    }
    @Override
    public void run() {
        //do something
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //done
        signal.setFiniashed(true);
    }
}


class ThreadB implements Runnable{
    SharedVarSignal signal;
    ThreadB( SharedVarSignal signal){
        this.signal = signal;
    }
    /**
     * 忙等待(Busy Wait)
     * 准备处理数据的线程 B 正在等待数据变为可用。
     * 换句话说，它在等待线程 A 的一个信号，这个信号使 hasFinishied()返回 true
     * 忙等待没有对运行等待线程的 CPU 进行有效的利用，除非平均等待时间非常短。
     * 否则，让等待线程进入睡眠或者非运行状态更为明智，直到它接收到它等待的信号。
     *
     */
    @Override
    public void run() {
        while(!signal.hasFinishied()){
            System.out.println("Not readey yet!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //After signal is finishied,do something
        System.out.println("I can do my job!");
    }
}
class SharedVarSignalTest{
    public static void main(String[] args){
        SharedVarSignal signal = new SharedVarSignal();
        (new Thread(new ThreadA(signal))).start();
        (new Thread(new ThreadB(signal))).start();
    }
}

/**
 *为什么在执行wait, notify时，必须获得该对象的锁？

 这是因为，如果没有锁，wait和notify有可能会产生竞态条件(Race Condition)。考虑以下生产者和消费者的情景：

 1.1生产者检查条件（如缓存满了）-> 1.2生产者必须等待

 2.1消费者消费了一个单位的缓存 -> 2.2重新设置了条件（如缓存没满） -> 2.3调用notifyAll()唤醒生产者

 我们希望的顺序是： 1.1->1.2->2.1->2.2->2.3

 但在多线程情况下，顺序有可能是 1.1->2.1->2.2->2.3->1.2。也就是说，在生产者还没wait之前，消费者就已经notifyAll了，这样的话，生产者会一直等下去。

 所以，要解决这个问题，必须在wait和notifyAll的时候，获得该对象的锁，以保证同步。
 */
class QueueBuffer {
    int n;
    boolean valueSet = false;

    synchronized int get() {
        if (!valueSet)//true
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        System.out.println("Got: " + n);
        valueSet = false;
        notify();
        return n;
    }

    synchronized void put(int n) {
        if (valueSet)//true
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        this.n = n;
        valueSet = true;
        System.out.println("Put: " + n);
        notify();
    }
}
class Producer implements Runnable {

    private QueueBuffer q;

    Producer(QueueBuffer q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    public void run() {
        int i = 0;
        while (true) {
            q.put(i++);
        }
    }

}

class Consumer implements Runnable {

    private QueueBuffer q;

    Consumer(QueueBuffer q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        while (true) {
            q.get();
        }
    }

}
class ConsumerProducerTest {

    public static void main(String[] args) {
        QueueBuffer q = new QueueBuffer();
        new Producer(q);
        new Consumer(q);
        System.out.println("Press Control-C to stop.");
    }

}
/**
 * 利用wait()和Notify
 * 交替输出1,2,1,2,1,2......
 */
 class WaitNotifyThread implements Runnable {

    private int num;
    private Object lock;

    public WaitNotifyThread(int num, Object lock) {
        super();
        this.num = num;
        this.lock = lock;
    }

    public void run() {
        try {
            while(true){
                synchronized(lock){
                    lock.notifyAll();
                    lock.wait();
                    System.out.println(num);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        final Object lock = new Object();

        Thread thread1 = new Thread(new WaitNotifyThread(1,lock));
        Thread thread2 = new Thread(new WaitNotifyThread(2, lock));
        Thread thread3 = new Thread(new WaitNotifyThread(3, lock));

        thread1.start();
        thread2.start();
        thread3.start();
    }

}



