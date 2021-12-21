package com.moon.joyce.commons.utils;

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
    /**
     * 正则匹配
     * @param var 需要匹配的参数
     * @param formula 正则表达式
     * @return
     */
    public static boolean matcherUtil(String var,String  formula) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        String s2=formula;
        if(StringUtils.isNotBlank(var)){
            p = Pattern.compile(s2);
            m = p.matcher(var);
            b = m.matches();
        }
        return b;
    }
}
