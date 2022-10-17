package com.moon.joyce.commons.annotation.joyce;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 21:52
 * @describe: 是否创建新属性
 */

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateFeild {
    boolean ifOld() default true;
}
