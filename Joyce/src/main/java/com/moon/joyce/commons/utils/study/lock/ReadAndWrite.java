package com.moon.joyce.commons.utils.study.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/11
 * 读写锁
 */
public class ReadAndWrite {
    /**
     * 读写锁：
     * 读锁又称共享锁（多个数据进行），读锁不可存在写锁，读锁存在写锁可能失败也可能不失败
     * 写锁又称独占锁（单个数据进行），写锁可存在读锁
     * 读写锁的作用是保证读写数据的安全
     */
    public static void main(String[] args) {
        RwMap2 rwMap = new RwMap2();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(
                    ()->{
                        rwMap.get(String.valueOf(finalI));
                    },String.valueOf(finalI)
            ).start();
        }
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(
                    ()->{
                        rwMap.put(String.valueOf(finalI),String.valueOf(finalI +1));
                    },String.valueOf(finalI)
            ).start();
        }

    }
     static class RwMap{
         Map<String, String> map = new HashMap<>();
         public void put(String k,String v){
             System.out.println("正在写入");
             map.put(k,v);
             System.out.println("写入完毕");
         }
         public String get(String k){
             System.out.println("正在读取");
             String str =  map.get(k);
             System.out.println("读取完毕");
             return str;
         }

    }
    static class RwMap2{
        Map<String, String> map = new HashMap<>();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        public void put(String k,String v){
            try {
                lock.readLock().lock();
                System.out.println("正在写入");
                map.put(k,v);
                System.out.println("写入完毕");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
          }

        public String get(String k){
            String str = null;
            try {
                lock.writeLock().lock();
                System.out.println("正在读取");
                str = map.get(k);
                System.out.println("读取完毕");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
                }
            return str;
            }
        }
}
