package com.moon.joyce.commons.utils;

import com.moon.joyce.example.functionality.entity.doma.JoyceException;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 14:20
 * @desc uuid工具类
 */
public class UUIDUtils implements Serializable {
    private static final long serialVersionUID = 8384493443192564756L;
    private UUIDUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    private final static int DEF_LEN = 8;
    /**
     * 获得一个纯数字字母的字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 获得一个长度为len的纯数字字母的字符串
     * @param len
     * @return
     */
    public static String getUUID(int len){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.length()>=len ? uuid.substring(0,len-1): uuid;
    }
    /**
     * 获得一个长度为len的纯数字字母的字符串的名字
     * @return
     */
    public static String getUUIDName(){
        return getUUID(DEF_LEN);
    }

}
