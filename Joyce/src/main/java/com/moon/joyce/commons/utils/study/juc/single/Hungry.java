package com.moon.joyce.commons.utils.study.juc.single;

import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/10
 */
public class Hungry {
    private int[] array= new int[1024];
    private List<Integer> list = Lists.newArrayList();
    private Hungry(){
    }
    //进来直接new对象，太消耗资源
    private static Hungry hungry= new Hungry();
    static Hungry newInstance(){
        System.out.println("hungry");
        return  hungry ;
    }
}
