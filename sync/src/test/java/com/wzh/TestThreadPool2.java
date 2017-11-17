/*
 * Copyright 2017 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */

package com.wzh;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * main()方法相当于一个残忍的领导，他派发出许多任务(produceTaskMaxNumber)，丢给一个叫 threadPool的任劳任怨的小组来做。
 这个小组里面队员至少有两个(corePoolSiez)，如果他们两个忙不过来，任务就被放到任务列表(ArrayBlockingQueue)里面。
 如果积压的任务过多，多到任务列表都装不下(超过3个)的时候，就雇佣新的队员来帮忙。但是基于成本的考虑，不能雇佣太多的队员，至多只能雇佣 4个(maximumPoolSize)。
 如果四个队员都在忙时，再有新的任务，这个小组就处理不了了，任务就会被通过一种策略来处理(DiscardOldestPolicy)，我们的处理方式是不停的派发，直到接受这个任务为止(更残忍！呵呵)。
 因为队员工作是需要成本的，如果工作很闲，闲到 3SECONDS都没有新的任务了(keepAliveTime)，那么有的队员就会被解雇了，但是，为了小组的正常运转，即使工作再闲，小组的队员也不能少于两个。
 * @author wangzhenhui
 * @date 2017/11/1710:24
 */
public class TestThreadPool2 {
    private static int produceTaskSleepTime = 2;
    private static int produceTaskMaxNumber = 20;

    public static void main(String[] args)
    {
        // 构造一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= produceTaskMaxNumber; i++)
        {
            try
            {
                // 产生一个任务，并将其加入到线程池
                String task = "task@ " + i;
                System.out.println("put " + task);
                threadPool.execute(new ThreadPoolTask(task));

                // 便于观察，等待一段时间
                Thread.sleep(produceTaskSleepTime);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try {
            threadPool.shutdown();
            if (!threadPool.awaitTermination(2, TimeUnit.HOURS)) {
                throw new RuntimeException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程池执行的任务
 */
class ThreadPoolTask implements Runnable, Serializable
{
    private static final long serialVersionUID = 0;
    private static int consumeTaskSleepTime = 20;
    // 保存任务所需要的数据
    private Object threadPoolTaskData;

    ThreadPoolTask(Object tasks)
    {
        this.threadPoolTaskData = tasks;
    }

    public void run()
    {
        // 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句
        System.out.println(Thread.currentThread().getName()+":start do .." + threadPoolTaskData);

        try
        {
            // //便于观察，等待一段时间
            Thread.sleep(consumeTaskSleepTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        threadPoolTaskData = null;
    }

    public Object getTask()
    {
        return this.threadPoolTaskData;
    }
}