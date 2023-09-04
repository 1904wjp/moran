package com.moon.joyce.commons.annotation.auto;

import com.moon.joyce.commons.factory.enums.TableStrategy;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/13-- 21:57
 * @describe: 实体类自动生成表的表注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    //名称
    String name() default "";
    //注释
    String content() default "";
    //创建策略
    TableStrategy strategy() default TableStrategy.ADD;
    //是否为父类，若为父类，则当前类不参加创建，但是其子类及其继承的属性会参加
    boolean isParent() default false;
}
