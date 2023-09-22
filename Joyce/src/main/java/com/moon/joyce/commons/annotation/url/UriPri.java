package com.moon.joyce.commons.annotation.url;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 14:55
 * @describe: url前缀
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UriPri {
    //名称
    String name() default "";
    //前缀
    String pri() default "";

    //场景
    String scene() default "";
}
