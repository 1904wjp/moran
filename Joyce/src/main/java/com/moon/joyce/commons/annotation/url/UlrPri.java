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
public @interface UlrPri {
    String name() default "";
    String pri() default "";
}
