package com.moon.joyce.commons.utils;

import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xing Dao Rong
 * @date 2021/9/1 16:38
 * @desc 正则工具类
 */
public class RegularUtils implements Serializable {
    private static final long serialVersionUID = -6630082575745822212L;
    private RegularUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    /**
     * 正则匹配
     * @param var 需要匹配的参数
     * @param formula 正则表达式
     * @return
     */
    public static boolean matcherUtil(String var,String  formula) {
        boolean b = false;
        if(StringUtils.isNotBlank(var) && StringUtils.isNoneBlank(formula)){
            b = Pattern.compile(formula).matcher(var).matches();
        }
        return b;
    }
}
