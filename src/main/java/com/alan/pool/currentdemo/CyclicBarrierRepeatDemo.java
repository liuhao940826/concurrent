package com.alan.pool.currentdemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: liuhao
 * @Description: Cyclic: 循环
 * barrier: 障碍
 * <p>
 * 字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。叫做回环是因为当所有等待线程都被释放以后，
 * CyclicBarrier可以被重用。我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了。
 * @Date: Create in 10:45 PM 2019/5/30
 */
public class CyclicBarrierRepeatDemo {


    public static void main(String[] args) {

        cyclicRepeatBarrier(4);
    }

    /**
     * 参数parties指让多少个线程或者任务等待至barrier状态
     * 如果需要在阻碍状态下做额外的事情 然后在让这组线程继续执行 添加runnable
     *  顺序 A>B>C
     * @param parties
     */
    public static void cyclicRepeatBarrier(int parties) {

        CountDownLatch latch = new CountDownLatch(parties);

        final CyclicBarrier barrier = new CyclicBarrier(parties, new Runnable() {

            //TODO 这里代表B
            public void run() {
                System.out.println("当前线程:"+Thread.currentThread().getName()+"这里会在到达了阻碍状态以后执行....");
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(parties);
        //多个线程去执行每个线程内部对应的东西
        doSomething(parties,executorService,barrier, latch);

        try {
            //当计数器为0时候继续执行下去
            latch.await();

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("计数器数量:"+latch.getCount());
        System.out.println("重用");
        //重新初始化计数器
        latch = new CountDownLatch(4);

        doSomething(parties,executorService,barrier, latch);

    }

    public static void doSomething(int parties ,ExecutorService executorService,final CyclicBarrier barrier,final CountDownLatch latch ){

        for (int i = 0; i < parties; i++) {

            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        //TODO 这里代表 A
                        System.out.println("当前线程:"+Thread.currentThread().getName()+"do something");

                        //用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
                        barrier.await();

                        //让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。
                        //barrier.await(3, TimeUnit.SECONDS);

                        //TODO 这里代表C
                        //只有其他子线程都执行了await 之后达到了barrier状态之后才会执行子线程下面这段代码
                        System.out.println("所有线程执行完毕..............等待其他的执行了才会执行到某一个线程的这里");

                        //减少一次计数器
                        latch.countDown();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

}
