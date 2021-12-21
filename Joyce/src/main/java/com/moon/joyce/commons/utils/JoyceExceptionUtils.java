package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import com.moon.joyce.example.functionality.entity.Result;

import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/11
 * JoyceException工具类
 */
public class JoyceExceptionUtils {
    /**
     * 异常方法
     * @param code
     * @param msg
     * @param type
     * @return
     */
    public static JoyceException exception(Integer code ,String msg, String type){
        return new JoyceException(code,msg,type);
    }

    /**
     * 异常方法
     * @param msg
     * @param type
     * @return
     */
    public static JoyceException exception(String msg, String type){
        return new JoyceException(Constant.ERROR_CODE,msg,type);
    }
}
