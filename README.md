
#1:CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

#2:Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
8个线程只有5个线程可以同一时间执行 侧重点是资源控制
