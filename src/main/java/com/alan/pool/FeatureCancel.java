package com.alan.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FeatureCancel {


  /**
   * 1）有界队列：
   * <p>
   * SynchronousQueue ：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于 阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法
   * Executors.newCachedThreadPool 使用了这个队列。 ArrayBlockingQueue：一个由数组支持的有界阻塞队列。此队列按
   * FIFO（先进先出）原则对元素进行排序。一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。 2）无界队列：
   * <p>
   * LinkedBlockingQueue：基于链表结构的无界阻塞队列，它可以指定容量也可以不指定容量（实际上任何无限容量的队列/栈都是有容量的，这个容量就是Integer.MAX_VALUE）
   * PriorityBlockingQueue：是一个按照优先级进行内部元素排序的无界阻塞队列。队列中的元素必须实现 Comparable
   * 接口，这样才能通过实现compareTo()方法进行排序。优先级最高的元素将始终排在队列的头部；PriorityBlockingQueue 不会保证优先级一样的元素的排序。
   * 注意：keepAliveTime和maximumPoolSize及BlockingQueue的类型均有关系。如果BlockingQueue是无界的，那么永远不会触发maximumPoolSize，自然keepAliveTime也就没有了意义。
   *
   * @param args
   */
  public static void main(String[] args) {

    ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(1, 1,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());

    for (int i = 0; i < 2; i++) {

      final int finalI = i;
      Future<Boolean> future = fixedThreadPool.submit(new Callable<Boolean>() {

        public Boolean call() throws Exception {
          System.out.println("当前线程:" + Thread.currentThread().getName() + "do something");
          if (finalI == 1) {
            System.out.println("当前线程:" + Thread.currentThread().getName() + "sleep");
            Thread.sleep(5000);
          }
          return true;
        }
      });

      try {
        System.out.println("feature.get");
        Object x = future.get(900, TimeUnit.MILLISECONDS);
        System.out.println(x);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        System.out.println("execute exception...");
        e.printStackTrace();
      } catch (TimeoutException e) {
        e.printStackTrace();
        System.out.println("超时异常...");
        future.cancel(true);
        System.out.println("任务取消:"+future.isCancelled());
      }

      System.out.println("激活数量"+fixedThreadPool.getActiveCount());
    }
  }
}
