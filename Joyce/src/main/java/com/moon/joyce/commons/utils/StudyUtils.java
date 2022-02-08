package com.moon.joyce.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.*;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/7
 * 学习工具类
 */
public class StudyUtils {
    public static void main(String[] args) {
     threadPoolsJoyce(StudyUtils.single,6);
    }

    /**
     * call()方法的实现
     * 有返回值
     * futureTask.get()可能会阻塞
     */
    public static void joyceCall(){
        FutureTask futureTask = new FutureTask(new JoyceCallThread());
        new Thread(futureTask, "abc").start();
        Integer o = null;
        try {
            o = (Integer) futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(o);
    }
    static class JoyceCallThread implements Callable {
        @Override
        public Integer call() throws Exception {
            System.out.println("call");
            return 2064;
        }
    }

    /**
     * 减法计数器（-）
     * countDownLatch.countDown()=-1
     * 当计数器减到0时将会唤醒countDownLatch.await()
     */
    public static void countDownLatchJoyce(){
        CountDownLatch countDownLatch = new CountDownLatch(8);
        for (int i = 0; i < 8; i++) {
            new Thread(()->{ System.out.println(Thread.currentThread().getName()+"执行了");countDownLatch.countDown(); },String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕");
    }
    /**
     * 加法计数器（+）
     * countDownLatch.countDown()=+1
     * 当计数器加到CyclicBarrier设置量时将会执行CyclicBarrier设置的线程，并且唤醒countDownLatch.await()
     */
    public static void cyclicBarrierJoyce(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8, ()->{
            System.out.println("已达到要求");
        });

        for (int i = 1; i <= 8; i++) {
            final int finalI = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"::"+finalI);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("执行完毕");
    }

    /**
     * 信号
     * semaphore.acquire()//获取资源
     * semaphore.release()//释放资源
     */
    public static void semaphoreJoyce(){
        //设置资源数是4
        Semaphore semaphore = new Semaphore(4);
        for (int i = 1; i <= 9; i++) {
           new Thread(()->{
               try {
                   semaphore.acquire();
                   System.out.println(Thread.currentThread().getName()+"获取资源");
                   TimeUnit.SECONDS.sleep(3);//阻塞三秒
                   System.out.println(Thread.currentThread().getName()+"离开资源");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }finally {
                  semaphore.release();
               }
           },String.valueOf(i)).start();
        }
        System.out.println("执行完毕");
    }

    /**
     * 阻塞队列
     * add()//添加超过初始容量就会报异常，返回为boolean;
     * remove()//移除小于0就会报异常，返回为boolean;
     * offer(),没有异常只有boolean
     * poll同上
     */
    public static void arrayBlockingQueueJoyce() throws InterruptedException {
        BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //超过指定范围
        System.out.println(blockingQueue.add("d"));
        System.out.println(blockingQueue.remove("a"));
        System.out.println(blockingQueue.remove("b"));
        System.out.println(blockingQueue.remove("c"));
        //阻塞队列已经为空
        System.out.println(blockingQueue.remove("d"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //超过指定范围
        System.out.println(blockingQueue.offer("d"));
        //超时插入
        System.out.println(blockingQueue.offer("d",2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //阻塞队列已经为空
        System.out.println(blockingQueue.poll());
        //超时等待
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println("执行完毕");
    }

    /**
     * 不存储元素，边加边出
     * put()添加元素
     * take()拿出元素
     * put()后必须take()才能继续put()
     */
    public static void synchronousQueueJoyce(){
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"put a");
                queue.put("a");//等"a"运行结束（出来）后"b"才会进入
                System.out.println(Thread.currentThread().getName()+"put b");
                queue.put("b");
                System.out.println(Thread.currentThread().getName()+"put c");
                queue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t1").start();
        new Thread(()->{

            try {
                System.out.println(Thread.currentThread().getName()+queue.take());
                System.out.println(Thread.currentThread().getName()+queue.take());
                System.out.println(Thread.currentThread().getName()+queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t1").start();
    }

    /**
     * 线程池三次方法
     */

    private static String  single = "single";
    private static String  fixed = "fixed";
    private static String  cached = "cached";
   public static void threadPoolsJoyce(String type,int number){

       if (StringUtils.isBlank(type)){
           return ;
       }
       ExecutorService threadPool = null;
       if (number==0){
           number=3;
       }
       switch (type){
           //单一线程池（始终只有一个线程）
           case "single": threadPool=Executors.newSingleThreadExecutor();break;
           //固定线程池（固定数值线程）
           case "fixed": threadPool=Executors.newFixedThreadPool(number);break;
           //缓存线程池（随着cpu和并发限制可大可小）
           case "cached": threadPool=Executors.newCachedThreadPool();break;
       }
       try {
           for (int i = 0; i < 100; i++) {
               threadPool.execute(()->{
                   System.out.println(Thread.currentThread().getName());
               });
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           threadPool.shutdown();
       }

   }
   //七大参数
    // corePoolSize:核心并发数量(必须开启的最小设置并发数量)
    // maximumPoolSize:最大并发数量
    // keepAliveTime:保持连接时间
    // unit:超时单位
    // workQueue：阻塞队列
    // Executors.defaultThreadFactory():线程池工厂
    // defaultHandler:线程池拒绝策略
}
