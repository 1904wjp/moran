package com.moon.joyce.commons.utils;

import com.moon.joyce.example.functionality.entity.JoyceException;



/**
 * @Author: XingDaoRong
 * @Date: 2021/12/11
 * JoyceException工具类
 */
public class JoyceExceptionUtils {
    private JoyceExceptionUtils() throws JoyceException {
        throw new JoyceException("工具类无法实例化");
    }


    /**
     * 异常方法
     * @param msg
     * @param throwable
     * @return
     */
    public static JoyceException exception( String msg,Throwable throwable){
        return new JoyceException(msg,throwable);
    }
    /**
     * 异常方法
     * @param msg
     * @return
     */
    public static JoyceException exception(String msg){
        return new JoyceException(msg);
    }

}
