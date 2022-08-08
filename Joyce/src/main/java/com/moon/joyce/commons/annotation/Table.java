package com.moon.joyce.commons.annotation;

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
    String name();
    String content() default "";
    TableStrategy strategy() default TableStrategy.ADD;
    boolean isParent() default false;
}
