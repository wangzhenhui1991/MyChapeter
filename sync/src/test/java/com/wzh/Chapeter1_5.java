package com.wzh;

import java.util.ArrayList;
import java.util.List;

/**
 * 饥饿
 * Created by wangzhenhui on 2017/8/14.
 */
public class Chapeter1_5 {
}
interface Lock{
    void lock()throws InterruptedException;
    void unlock();
}

/**
 * 锁
 */
 class NormalLock implements Lock{

    private boolean isLocked      = false;

    private Thread lockingThread = null;

    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        notifyAll();
    }
}
class Synchronizer{
    Lock lock ;
    Synchronizer(Lock lock){
        this.lock = lock;
    }
    public void doSynchronized() throws InterruptedException{
        this.lock.lock();
        //critical section, do a lot of work which takes a long time
        System.out.println(Thread.currentThread().getName());
        this.lock.unlock();
    }
}
class SynchronizerThread implements Runnable{
    Synchronizer worker;
    SynchronizerThread(Synchronizer worker){
        this.worker = worker;
    }
    @Override
    public void run() {
        try {
            while(true) {
                worker.doSynchronized();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class LockTest{
    public static void main(String[] args){
        Synchronizer worker = new Synchronizer(new NormalLock());
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
    }
}


/**
 *
 */
 class FairLock implements Lock{
    private boolean           isLocked       = false;
    private Thread            lockingThread  = null;
    private List<QueueObject> waitingThreads =
            new ArrayList<QueueObject>();

    public void lock() throws InterruptedException{
        QueueObject queueObject           = new QueueObject();
        boolean     isLockedForThisThread = true;
        synchronized(this){
            waitingThreads.add(queueObject);
        }

        while(isLockedForThisThread){
            synchronized(this){
                isLockedForThisThread =
                        isLocked || waitingThreads.get(0) != queueObject;
                if(!isLockedForThisThread){
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try{
                queueObject.doWait();
            }catch(InterruptedException e){
                synchronized(this) { waitingThreads.remove(queueObject); }
                throw e;
            }
        }
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked      = false;
        lockingThread = null;
        if(waitingThreads.size() > 0){
            waitingThreads.get(0).doNotify();
        }
    }
}
class QueueObject {

    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {

        while(!isNotified){
            this.wait();
        }

        this.isNotified = false;

    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    public boolean equals(Object o) {
        return this == o;
    }

}
class FairLockTest{
    public static void main(String[] args){
        Synchronizer worker = new Synchronizer(new FairLock());
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
        (new Thread(new SynchronizerThread(worker))).start();
    }
}
