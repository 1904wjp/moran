package com.moon.joyce.commons.annotation.auto;

import java.lang.annotation.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/26-- 13:57
 * @describe:默认属性
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ids {
    //主键绑定
    String[] values() default {"id"};
    //唯一属性绑定
    String[] uniques() default {"id"};
}
