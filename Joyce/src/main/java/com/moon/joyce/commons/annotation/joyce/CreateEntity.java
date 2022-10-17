package com.moon.joyce.commons.annotation.joyce;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 21:46
 * @describe: 是否自动创建类型
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateEntity {
   boolean ifCreate() default true;
   String name() default "";
}
