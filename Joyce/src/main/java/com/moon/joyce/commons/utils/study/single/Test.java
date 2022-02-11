package com.moon.joyce.commons.utils.study.single;

import com.moon.joyce.example.functionality.entity.JoyceException;

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
