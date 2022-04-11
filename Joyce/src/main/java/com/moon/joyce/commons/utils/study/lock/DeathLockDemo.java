package com.moon.joyce.commons.utils.study.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/12
 * 死锁
 */
public class DeathLockDemo {
    public static void main(String[] args) {
        new Thread(new MyThread("1","2"),"t1"
       ).start();
     new Thread(new MyThread("2","1"),"t2"
            ).start();
}
 static class MyThread implements Runnable{
    private String s1;
    private String s2;

     public MyThread(String s1, String s2) {
         this.s1 = s1;
         this.s2 = s2;
     }

     @Override
     public void run() {
         synchronized (s1){
             System.out.println(Thread.currentThread().getName());
             try {
                 TimeUnit.SECONDS.sleep(3);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             synchronized (s2){
                 System.out.println(Thread.currentThread().getName());
             }
         }
     }
 }
}
// 命令：jps -l
// 使用jstack 进程进程号 找到死锁信息