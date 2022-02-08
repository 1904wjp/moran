package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.utils.entity.ForkJoinDemo;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/7
 * 学习工具类
 */
public class StudyUtils {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //addJoyce();//sum=500000000500000000::7078
        //forkJoinJoyce();//sum=500000000500000000::5832
        addStreamJoyce();//sum=500000000500000000::393
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
     * 线程池三中方法（不建议使用）
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
    // maximumPoolSize:最大并发数量(多余开启窗口数量)
    // keepAliveTime:保持连接时间
    // unit:超时单位
    // workQueue：阻塞队列(等待区的位子数量)
    // Executors.defaultThreadFactory():线程池工厂
    // defaultHandler:线程池拒绝策略

    /**
     * 自定义线程池及其四个拒绝策略
     * @param type
     */

    private static String abortPolicy = "abortPolicy";
    private static String callerRunsPolicy = "callerRunsPolicy";
    private static String discardOldestPolicy = "discardOldestPolicy";
    private static String discardPolicy = "discardPolicy";
    public static void threadPolicy(String type){
        ThreadPoolExecutor threadPoolExecutor = null;
        /**
         * CPU 密集型 查看运行的系统是几核，那么maxSize就选择是几个
         * IO 密集型 IO线程有多少，那么maxSize就大于它，一般是两倍
         */
        int maxSize = 0;
        //获取系统核数量
        maxSize = Runtime.getRuntime().availableProcessors();
        switch (type){
            //超过最大承载会拒绝线程进入，抛出异常
            case "abortPolicy":threadPoolExecutor =new ThreadPoolExecutor(1, maxSize, 3,TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy() );break;
            //超过最大承载会拒绝线程进入，拒绝的线程会回到调用线程的（哪来回哪儿去）
            case "callerRunsPolicy":threadPoolExecutor =new ThreadPoolExecutor(2, maxSize, 3,TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy() );break;
            //超过最大承载会拒绝线程进入，不会抛出异常
            case "discardOldestPolicy":threadPoolExecutor =new ThreadPoolExecutor(2, maxSize, 3,TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy() );break;
            //超过最大承载会尝试和最早的线程竞争
            case "discardPolicy":threadPoolExecutor =new ThreadPoolExecutor(2, maxSize, 3,TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy() );break;
        }

            try {
                int len = 8;//最大承载是maxSize + 阻塞队列中的数量
                for (int i = 1; i <= len; i++) {
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                threadPoolExecutor.shutdown();
            }
    }

    /**
     * Function函数式接口
     * 参数为Object
     * 返回值为String
     */
    public static void functionJoyce(){
     /*   Function function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        };*/
        Function<Object,String> function=s-> s.toString();
        System.out.println(function);
    }
    /**
     * Predicate 断定函数式接口
     * 参数为String
     * 返回值为Boolean
     */
    public static void predicateJoyce(){
      /*  Predicate predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };*/
        Predicate<String> predicate =str->str.isEmpty();
        System.out.println(predicate);
    }

    /**
     * 消费型接口，定义String参数
     */
    public static void consumerJoyce(){
       /* Consumer consumer = new Consumer<String> () {
            @Override
            public void accept(String o) {
                System.out.println(o);
            }
        };*/
        Consumer<String> consumer = str->{

        };
        System.out.println(consumer);

    }
    /**
     * 消费型接口，定义String参数
     */
    public static void supplierJoyce(){
       /* Supplier supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "123";
            }
        };*/
        Supplier<String> supplier = ()->{return "123456";};
        System.out.println(supplier);
    }
    public static void addJoyce()  {
        long startTime = System.currentTimeMillis();
        Long sum = 0l;
        for (long i = 0; i <= 10_0000_0000l; i++) {
            sum+=i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("sum="+sum+"::"+(endTime-startTime));
    }
    //forkJoin操作
    public static void forkJoinJoyce() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo task = new ForkJoinDemo(0l, 10_0000_0000l);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long sum = 0l;
        sum = submit.get();
        long endTime = System.currentTimeMillis();
        System.out.println("sum="+sum+"::"+(endTime-startTime));
    }
    public static void addStreamJoyce()  {
        long startTime = System.currentTimeMillis();
        Long sum = LongStream.rangeClosed(0l,10_0000_0000l).parallel().reduce(0,Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("sum="+sum+"::"+(endTime-startTime));
    }
}
