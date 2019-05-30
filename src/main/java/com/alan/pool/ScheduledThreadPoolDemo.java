package com.alan.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhao
 * @Description:
 * @Date: Create in 4:50 PM 2019/5/30
 */
public class ScheduledThreadPoolDemo {

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
     * 延迟3 秒执行
     * @param args
     */
    public static void main(String[] args) {

        ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPool.schedule(new Runnable() {

            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);


    }
}
