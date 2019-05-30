package com.alan.pool.currentdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: liuhao
 * @Description: Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * @Date: Create in 11:50 PM 2019/5/30
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        //同一时间 最多允许5个并行, 但是有8个线程
        semaphore(5,8);
    }

    /**
     * @param permits 许可的数量
     * @param num     并行的数量
     */
    public static void semaphore(int permits, int num) {
        //参数permits表示许可数目，即同时可以允许多少线程进行访问
//      Semaphore semaphore = new Semaphore(permits);
        //这个多了一个参数fair表示是否是公平的，即等待时间越久的越先获取许可
        final Semaphore semaphore = new Semaphore(permits, true);

        ExecutorService executorService = Executors.newFixedThreadPool(num);

        for (int i = 0; i < num; i++) {

            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("当前线程:"+Thread.currentThread().getName()+"-获取许可");

                        Thread.sleep(2000L);
                        semaphore.release();
                        System.out.println("当前线程:"+Thread.currentThread().getName()+"-释放许可");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }
}
