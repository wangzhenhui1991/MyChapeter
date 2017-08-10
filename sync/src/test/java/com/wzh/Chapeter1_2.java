package com.wzh;

import org.junit.Assert;

/**
 *
 */

public class Chapeter1_2 {

    /**
     * 设计一个main函数的主线程去主动中断线程t，并让t捕获自己被中断的异常
     * @param args
     */
    public static void main(String[] args){
        Thread t =  new Thread(new InterruptThread());
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}

/**
 * 1.线程中断:
 * 使用 interrupt()中断线程:当一个线程运行时，另一个线程可以调用对应的 Thread 对象的 interrupt()方法来中断它，该方法只是在目标线程中设置一个标志，表示它已经被中断，并立即返回。
 */
class InterruptThread implements Runnable{

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName()+":I'm doing something!");
        }
        System.out.println(Thread.currentThread().getName()+":I'm been interrupted!");

    }
    /**
     * 设计一个main函数的主线程去主动中断线程t，并让thread通过isInterrupted()判断是否被中断
     * @param args
     */
    public static void main(String[] args){
        Thread t =  new Thread(new InterruptThread());
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
class InterrupteThread2 implements Runnable{

    @Override
    public void run() {
        try{
            while(true){
                System.out.println(Thread.currentThread().getName()+":I'm doing something!");
                Thread.sleep(10);
            }
        }catch (InterruptedException e){
            System.out.println(":I'm been interrupted!");
        }
    }


    /**
     * 设计一个main函数的主线程去主动中断线程t，并让t捕获InterruptedException被中断的异常
     * @param args
     */
    public static void main(String[] args){
        Thread t =  new Thread(new InterrupteThread2());
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}

/**
 *  2.待决中断
 *  如果线程在调用 sleep()方法前被中断，那么该中断称为待决中断，它会在刚调用 sleep()方法时，立即抛出 InterruptedException 异常。
 */
class PendingThread {

    public static void main(String[] args){
        // 随机数%2==0,则在mian线程中中断当前线程（亦即main线程）
        // 随机数%2!=0,则不在main线程中中断当前线程
        int random = (int)(Math.random()*100);
        System.out.println(random);
        if( random%2==0 ){
            Thread.currentThread().interrupt();
        }
        //获取当前时间
        long startTime = System.currentTimeMillis();
        try{
            Thread.sleep(2000);
            System.out.println("was NOT interrupted");
        }catch(InterruptedException x){
            //或者可以通过Thread.currentThread().isInterrupted()==true 判断是否被中断
            System.out.println("was interrupted");
        }finally {
            //计算中间代码执行的时间
            System.out.println("elapsedTime=" + ( System.currentTimeMillis() - startTime));
        }
    }
}


/**
 * 3. 使用 isInterrupted()方法判断中断状态
 * 可以在 Thread 对象上调用 isInterrupted()方法来检查任何线程的中断状态。
 * 这里需要注意：线程一旦被中断，isInterrupted()方法便会返回 true，
 * 而一旦 sleep()方法抛出异常，它将清空中断标志，此时isInterrupted()方法将返回 false。
 */
class InterruptedCheck {

    public static void main(String[] args){
        Thread.currentThread().interrupt();
        if(Thread.currentThread().isInterrupted()){
            System.out.println("I have been interrupted!");
        }

        try {
            Thread.sleep(200);
            System.out.println("I have been awaken by Thread.sleep();");
        } catch (InterruptedException e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("I'm still interrupted!");
            }else{
                System.out.println("I'm not interrupted!");
            }
        }
    }
}

/**
 * 4. 使用 Thread.interrupted()方法判断中断状态
 * 可以使用 Thread.interrupted()方法来检查当前线程的中断状态（并隐式重置为 false）。
 * 又由于它是静态方法，因此不能在特定的线程上使用，而只能报告调用它的线程的中断状态，如果线程被中断，而且中断状态尚不清楚，那么，这个方法返回 true。
 * 与 isInterrupted()不同，它将自动重置中断状态为 false，第二次调用 Thread.interrupted()方法，总是返回 false，除非中断了线程。
 */
class InterruptReset{

    public static void main(String[] args) {
        System.out.println(
                "Point X: Thread.interrupted()=" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println(
                "Point Y: Thread.interrupted()=" + Thread.interrupted());
        System.out.println(
                "Point Z: Thread.interrupted()=" + Thread.interrupted());
    }
}

/**
 *  5. join 方法用线程对象调用，
 *  如果在一个线程 A 中调用另一个线程 B 的 join 方法，线程 A 将会等待线程 B 执行完毕后再执行。
 */
class JoinThread implements Runnable{

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-startTime<1000){
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
        }
    }
    public static void main(String[] args){
        Thread t = new Thread(new JoinThread());
        t.start();
        try {
            t.join();//main线程调用t.join()，main要等t执行完成之后才会执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());

    }
}