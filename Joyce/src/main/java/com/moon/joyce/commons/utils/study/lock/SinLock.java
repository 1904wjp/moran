package com.moon.joyce.commons.utils.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/11
 */
public class SinLock {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(
                ()->{
                    lock.myLock();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unMyLock();
                    }
                },"t1"
        ).start();
        new Thread(
                ()->{
                    lock.myLock();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unMyLock();
                    }
                },"t2"
        ).start();
    }
    static AtomicReference<Thread> atomicReference = new AtomicReference<>();
    static class MyLock{
        public void myLock(){
            Thread thread = Thread.currentThread();
            System.out.println("lock的"+thread.getName());
            while (!atomicReference.compareAndSet(null,thread)){
                System.out.println(thread.getName()+"自旋中");
            }
        }
        public void unMyLock(){
            Thread thread = Thread.currentThread();
            System.out.println("unLock的"+thread.getName());
            atomicReference.compareAndSet(thread,null);
            System.out.println(thread.getName()+":unlock");

        }
    }
}
