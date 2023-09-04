package com.moon.joyce.commons.annotation.url;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 14:57
 * @describe: 接口方法
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodUrl {
    //名称
    String name() default "";
    //链接
    String url() default "";
    //参数
    String params() default "";
}
