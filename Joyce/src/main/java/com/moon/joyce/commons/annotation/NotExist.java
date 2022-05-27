package com.moon.joyce.commons.annotation;

import com.moon.joyce.commons.factory.enums.Type;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/13-- 21:57
 * @describe: 实体类自动生成表的列注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExist {
    boolean require() default true;
}
