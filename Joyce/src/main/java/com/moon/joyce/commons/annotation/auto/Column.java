package com.moon.joyce.commons.annotation.auto;

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
public @interface Column {
    String name() default "";
    String length() default "64";
    Type type() default Type.VARCHAR;
    String comment() default "";
    boolean isKey() default false;
    boolean auto() default false;
    boolean isNotNull() default false;
    String defaultValue() default "NULL";
    boolean unique() default false;
    boolean exist() default true;
}
