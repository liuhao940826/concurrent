package com.alan.pool.currentdemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: liuhao
 * @Description: CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。比如有一个任务A，
 * 它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了
 * @Date: Create in 10:33 PM 2019/5/30
 */
public class  CountDownLatchDemo {


    public static void main(String[] args) {

        //计数器
        final CountDownLatch latch = new CountDownLatch(4);

        System.out.println("latch 初始化的数值:" + latch.getCount());

        //创建最大3个并行的线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //需要循环让线程池去跑多个任务
        for (int i = 0; i < 4; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    //计数器减少
                    latch.countDown();

                    //这里可能输出相同的数字,不影响这里主要显示计数器为0
                    System.out.println("latch 当前的数字:" + latch.getCount());
                }
            });

        }

        try {
            //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
            latch.await();
            //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
//            latch.await(1L, TimeUnit.SECONDS);
            System.out.println("只有当计数器为0的时候这里才会出现........");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
