package com.wzh;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wangzhenhui on 2017/8/15.
 */
public class Chapeter1_1_2 {
}

class RunnableTask implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"is doing something!");
    }
}

class RunnableMain{
    public static void main(String[] args){
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            es.submit(new RunnableTask());
        }
    }
}


class CallableTask implements Callable<String>{

    @Override
    public String call() throws Exception {
        double  random =Math.random();
        if((random*100)%2==0){
            return "SUCCESS";
        }else {
            return "FAIL";
        }
    }
}

class CallableMain{
    public static void mian(String[] args){
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            es.submit(new CallableTask());
        }
    }
}

class FutureMain{
    public static void main(String[] args){
        List<Future<String>> results = new ArrayList<>();
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            Future<String> res = es.submit(new CallableTask());
            try {
                System.out.println(res.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}