package com.wzh;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该测试用例是模拟线程池下执行多线程，对同一个HashMap操作
 * Created by wangzhenhui on 2017/10/10.
 */
public class ExecutorPoolMain {

    public static void main(String[] args) throws InterruptedException {
        for(int j =1;j<100000;j++) {
            ExecutorPool executorPool = new ExecutorPool();
            long startTime = System.currentTimeMillis();
            for (int i = 1; i < 10; i++) {
                executorPool.put(i);
            }
            executorPool.waitUntilDone();
            System.out.println(System.currentTimeMillis() - startTime + "ms");
            try {
                Assert.assertEquals(executorPool.getMap().get(1).get(), 18);
                Assert.assertEquals(executorPool.getMap().get(2).get(), 54);
                for (Map.Entry entry : executorPool.getMap().entrySet()) {
                    Integer key = (Integer) entry.getKey();
                    AtomicInteger value = (AtomicInteger) entry.getValue();
                    System.out.println(key + "," + value.get());
                }
            }catch (Exception e){
                System.out.println("error:"+j);
            }
        }
    }
}

/**
 * 线程池
 */
class ExecutorPool {


    public ThreadPoolExecutor threadPoolExecutor;


    private final int corePoolSize = 10;
    private final int maxPoolSize = 10;
    private final int keepAliveTime = 1000;



    private HashMap<Integer,AtomicInteger> map;

    ExecutorPool(){
        map = new HashMap<>();
        initExecutor();
    }

    /**
     * 依次执行
     * @param data
     * @throws InterruptedException
     */
    public void put(Object data) throws InterruptedException {
        threadPoolExecutor.execute(() -> {
            try {
                new statis().doSthWithSync((Integer) data, map);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        new statis().doSth((Integer) data, map);
    }


    public void waitUntilDone() {
        try {
            threadPoolExecutor.shutdown();
            if (!threadPoolExecutor.awaitTermination(2, TimeUnit.HOURS)) {
                throw new RuntimeException();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 初始化线程池
     */
    private void initExecutor() {
        /**
         * 使用SynchronousQueue, CallerRunsPolicy的原因： 对账使用线程池的主要原因是利用多线程来提升对账处理速度，
         * 所以内存队列为SynchronousQueue，相当于queue设置为0，CallerRunsPolicy含义为当线程池繁忙时让主线程执行
         */
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1),
                new ThreadFactory() {
                    private AtomicInteger count = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "checkThread-" + count.incrementAndGet());
                    }
                });
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public HashMap<Integer, AtomicInteger> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, AtomicInteger> map) {
        this.map = map;
    }
}

/**
 * 执行业务
 */
class statis{

    private int breakPoint = 4;

    /**
     * 线程安全
     * @param data
     * @param map
     * @throws InterruptedException
     */
    public void doSthWithSync(Integer data,HashMap<Integer, AtomicInteger> map) throws InterruptedException {

        int group = data%breakPoint==0?1:2;
        System.out.println(Thread.currentThread().getName()+",group:"+group+",data:"+data);
        Thread.sleep(1l);
        if(map.containsKey(group)){
            map.get(group).addAndGet(data);
        }else{
            synchronized (map) {
                if(map.containsKey(group)){
                    map.get(group).addAndGet(data);
                }else {
                    map.put(group, new AtomicInteger(data));
                }
            }
        }
        map.get(group).incrementAndGet();
        Thread.sleep(1l);
        map.get(group).incrementAndGet();
        Thread.sleep(1l);
        map.get(group).incrementAndGet();
        Thread.sleep(1l);
    }

    /**
     * 非线程安全
     * @param data
     * @param map
     * @throws InterruptedException
     */
    public void doSthWithNoSync(Integer data,HashMap<Integer, AtomicInteger> map) throws InterruptedException{
        int group = data%breakPoint==0?1:2;
        System.out.println(Thread.currentThread().getName()+",group:"+group+",data:"+data);
        Thread.sleep(1l);
        if(map.containsKey(group)){
            map.get(group).addAndGet(data);
        }else{
            map.put(group,new AtomicInteger(data));
        }
        map.get(group).incrementAndGet();
        map.get(group).incrementAndGet();
        map.get(group).incrementAndGet();
    }

}