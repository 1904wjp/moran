package com.moon.joyce.commons.annotation;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/10-- 11:17
 * @describe:
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisValueComponet {
    String value() ;
}
