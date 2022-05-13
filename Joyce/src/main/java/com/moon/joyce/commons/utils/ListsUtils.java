package com.moon.joyce.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/04/12-- 11:13
 * @describe:
 */
public class ListsUtils {
    /**
     * 一行一行读取文件转成字符串集合
     * @param path
     * @return
     */
    public static List<String> readyLineFileConvertList(String path) {
        BufferedReader readObj = FileUtils.getReadObj(path);
        List<String> list = new ArrayList<>();
        String text;
        try {
            while ((text = readObj.readLine()) != null) {//使用readLine方法，一次读一行
                list.add(text);
            }
            readObj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 集合相加
     * @param param1
     * @param param2
     * @return
     */
    public static List<String> addList(List<String> param1, List<String> param2) {
        param1.addAll(param2);
        return param1;
    }

    //两个集合的和集or差集
    public static List<String> differOrIntersList(List<String> list1,List<String> list2,boolean flag){
        List<String> l1 = list1.size() >= list2.size() ? list1:list2;
        List<String> l2 = l1==list1 ? list2:list1;
        if (flag){
            return l1.stream().filter(itme -> !l2.contains(itme)).collect(Collectors.toList());
        }
        return  l1.stream().filter(l2::contains).collect(Collectors.toList());
    }
}
