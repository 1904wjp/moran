package com.moon.joyce.commons.joyce;


/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 22:53
 * @describe:
 */
public class Joyce {
    public static void main(String[] args) {
    }


    public static String str2(String str){
        return str.replaceAll("[^(\\一-\\龥)]", "").replace("\uE67C","").trim();
    }

}
