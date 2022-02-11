package com.moon.joyce.commons.utils.study.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/11
 */
public class FairAndUnfair {
    /**
     * 公平锁和非公平锁
     * 公平锁:按照正常的线程顺序去执行；
     * 创建方式:new ReentrantLock(true)
     * 非公平锁：线程会插队执行；
     * 创建方式:new ReentrantLock()
     */
    public static void main(String[] args) {
        FairAndUnfair fairAndUnfair = new FairAndUnfair();
//        fairAndUnfair.test1();
        fairAndUnfair.test2();
    }
    ReentrantLock  fairLock = new ReentrantLock(true);
    ReentrantLock  unFairLock = new ReentrantLock();
    public  void test1(){
        for (int i = 0; i < 2000; i++) {
            try {
                fairLock.lock();
                new Thread(()->{
                    System.out.println(Thread.currentThread().getName());
                }, i +"").start();
            } finally {
                fairLock.unlock();
            }
        }
    }
    public  void test2(){
        for (int i = 0; i < 2000; i++) {
            try {
                fairLock.lock();
                new Thread(()->{
                    System.out.println(Thread.currentThread().getName());
                }, i +"").start();
            } finally {
                fairLock.unlock();
            }
        }
    }
}
