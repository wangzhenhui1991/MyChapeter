package com.wzh;

/**
 * Created by wangzhenhui on 2017/10/25.
 */
public class VolatileTest {




    public static void main(String[] args) throws InterruptedException {

        VolatileNumber vol1 = new VolatileNumber();
        Thread thread = new Thread(vol1);
        thread.start();

        Thread thread2 = new Thread(vol1);
        thread2.start();

        Thread thread3 = new Thread(vol1);
        thread3.start();
        Thread thread4 = new Thread(vol1);
        thread4.start();
        Thread thread5 = new Thread(vol1);
        thread5.start();
        Thread thread6 = new Thread(vol1);
        thread6.start();
        Thread thread7 = new Thread(vol1);
        thread7.start();
//        Thread.sleep(2000l);

//        vol1.changeFlag1();
    }

}

class Volatile implements Runnable {
    private  boolean          flag1 = true;
    private volatile boolean flag2 = true;
    public Volatile(){

    }
    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public void changeFlag1(){
        flag1 = false;
    }

    public void changeFlag2(){
        flag2 = true;
    }
    @Override
    public void run() {
        while(flag2){
            if(Thread.currentThread().getId()%4==0){
                try {
                    Thread.sleep(2000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    flag2=false;
                    System.out.println(Thread.currentThread().getId()+"changed flag1!!!");
                }

            }

            System.out.println(Thread.currentThread().getId()+":"+flag1+","+flag2);
        }
        System.out.println(Thread.currentThread().getId()+":flag1 has been changed!");
    }
}

class VolatileNumber implements Runnable{

    private int count1 =0;
    private volatile int count2 = 0;

    public VolatileNumber(){

    }
    @Override
    public void run() {
        while(count2<10000) {
            count2++;
            System.out.println(Thread.currentThread().getName()+":" + count2);
        }
    }
}