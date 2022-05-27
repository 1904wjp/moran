package com.moon.joyce.commons.annotation;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/26-- 13:57
 * @describe:
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ids {
    String[] name() default {"id"};
}
