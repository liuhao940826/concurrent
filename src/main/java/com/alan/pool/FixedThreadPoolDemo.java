package com.alan.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhao
 * @Description:
 * @Date: Create in 4:49 PM 2019/5/30
 */
public class FixedThreadPoolDemo {


    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
     * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
     * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()。可参考PreloadDataCache。
     *
     * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，
     * 如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
     *
     * @param args
     */
    public static void main(String[] args) {

        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        //核心线程池和最大线程池保持一致 同一时间最多执行的
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        ;
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
