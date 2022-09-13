package com.moon.joyce.commons.utils.study.juc.single;

import com.moon.joyce.example.functionality.entity.doma.JoyceException;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/10
 * 单例模式测试类
 */
public class Test {
    @org.junit.Test
    public void test01(){
        System.out.println(Hungry.newInstance());

    }
    @org.junit.Test
    public void test02() throws JoyceException {
        System.out.println(LazyMan.newInstance());

    }
}
