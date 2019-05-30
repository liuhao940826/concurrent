package com.alan.pool;

import java.util.concurrent.*;

/**
 * @Author: liuhao
 * @Description:
 * @Date: Create in 2:22 PM 2019/5/30
 */
public class CacheThreadPoolDemo {


    /**
     *
     * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
     * 那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
     * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
     *
     * @param args
     */
    public static void main(String[] args) {

        //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。示例代码如下
        //
//         ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        // 创建线程池，其中核心线程0，也是我期望的最大并发数，最大线程数和队列大小都为30，即我的总任务数
        ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(0, 30,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        for (int i = 0; i < 100; i++) {
            final int index = i;
            System.out.println("当前i:"+i);
            cachedThreadPool.execute(new Runnable() {

                public void run() {
                    System.out.println("当前线程:"+Thread.currentThread().getName()+":"+index);
                }
            });
        }
        //主线程不等待其他线程执行完成可能就被先执行了
        System.out.println("主线程执行到了这里.....  ");
    }

}
