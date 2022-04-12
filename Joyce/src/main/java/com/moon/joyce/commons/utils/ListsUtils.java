package com.moon.joyce.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        for (String o : param2) {
            if (!StringsUtils.listIsContainsStr(o,param1)){
                param1.add(o);
            }
        }
        return param1;
    }
}
