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
    //名称
    String name() default "";
    //长度
    String length() default "64";
    //类型
    Type type() default Type.VARCHAR;
    //注释
    String comment() default "";
    //是否为主键
    boolean isKey() default false;
    //是否自动自动增长
    boolean auto() default false;
    //是否为null
    boolean isNotNull() default false;
    //默认值
    String defaultValue() default "NULL";
    //是否唯一
    boolean unique() default false;
    //是否存在
    boolean exist() default true;
}
