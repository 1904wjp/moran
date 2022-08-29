package com.moon.joyce.commons.annotation.url;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 14:57
 * @describe:
 */
public @interface MethodUrl {
    String name() default "";
    String url() default "";
    String params() default "";
}
