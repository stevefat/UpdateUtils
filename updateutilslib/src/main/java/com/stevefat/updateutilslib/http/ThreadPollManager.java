package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-14.
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-14 上午9:45
 */
public class ThreadPollManager {
    //线程池
    private ThreadPoolExecutor threadPoolExecutor;
    //请求队列
    private LinkedBlockingQueue<Future<?>> blockingQueue = new LinkedBlockingQueue();

    private final static ThreadPollManager   instance= new ThreadPollManager();


    public static ThreadPollManager getInstance() {
        return instance;
    }

    public ThreadPollManager() {
        threadPoolExecutor = new ThreadPoolExecutor(4,10,5, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),rejectedExecutionHandler);

        //请求队列开始执行
        threadPoolExecutor.execute(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    FutureTask futureTask = (FutureTask) blockingQueue.take();
                    if(futureTask!=null){
                        //请求拿到线程池里面执行
                        threadPoolExecutor.execute(futureTask);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 将请求放到请求队列里面
     * @param futureTask
     * @param <T>
     */
    public <T> void add(FutureTask<T> futureTask){
        if (futureTask != null ) {

            try {
                blockingQueue.put(futureTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 拒绝策略
     * 如果线程长久不执行,放到队列里面后面在执行
     */
    RejectedExecutionHandler rejectedExecutionHandler =new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                //重新放到请求队列里面
                blockingQueue.put(new FutureTask(runnable,null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
