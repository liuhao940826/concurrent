package com.alan.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: liuhao
 * @Description:
 * @Date: Create in 4:50 PM 2019/5/30
 */
public class SingleThreadPoolDemo {


    /**
     * 创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。
     * 重点:如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。
     * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

                public void run() {
                    try {

                        System.out.println("当前线程名字"+Thread.currentThread().getName()+"数字"+index);

                        Thread.sleep(2000);
                        if(index==2){
                            int j=1/0;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }


}
