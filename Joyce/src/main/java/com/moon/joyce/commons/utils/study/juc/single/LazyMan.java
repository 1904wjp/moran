package com.moon.joyce.commons.utils.study.juc.single;

import com.moon.joyce.commons.utils.JoyceExceptionUtils;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;
import org.apache.commons.compress.utils.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/10
 */
//懒汉模式
public  class LazyMan{
    private int[] array= new int[1024];
    private List<Integer> list = Lists.newArrayList();
    private Boolean flag = false;
    private LazyMan() throws JoyceException {
       synchronized (LazyMan.class){
            if (lazyMan==null){
                throw JoyceExceptionUtils.exception("不能用反射获取");
            }
       }
        System.out.println(Thread.currentThread().getName()+"ok");
    }

    private volatile static LazyMan lazyMan;
    public static LazyMan newInstance() throws JoyceException {
        //双重检测
        //不同步判断
        if (lazyMan==null){
            //保证线程安全
            synchronized (LazyMan.class){
                //同步判断
                if (lazyMan==null){
                    return new LazyMan();
                }
            }
        }
        return  lazyMan ;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, JoyceException {
        //LazyMan lazyMan = LazyMan.newInstance();
        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyMan lazyMan = declaredConstructor.newInstance();
        LazyMan lazyMan1 = declaredConstructor.newInstance();
        System.out.println(lazyMan+"::"+lazyMan1);
    }

}