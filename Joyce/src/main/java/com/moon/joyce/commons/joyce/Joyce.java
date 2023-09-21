package com.moon.joyce.commons.joyce;


import com.moon.joyce.commons.utils.JoyceExceptionUtils;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 22:53
 * @describe:
 */
public class Joyce {
    public static void main(String[] args) {
        String s = str3("5");
        System.out.println(s);
    }


    public static String str2(String str){
        if (str.equals("2")){
            throw JoyceExceptionUtils.exception("异常");
        }
        return "1";
    }

    public static String str3(String str){
        String s = null;
        try {
            s = str2(str);
        } catch (Exception e) {
            s= "0";
        }
        return s;
    }
}
