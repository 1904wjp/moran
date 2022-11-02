package com.moon.joyce.commons.utils;

import java.util.Objects;
import java.util.Properties;

public class CommonUtils {
    /**
     * 获取zfbinfo.properties文件里的值
     * @param name key
     * @return
     * @throws Exception
     */
    public String getZFBinfoValue(String name) throws Exception{
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/joyce.properties"));
        String filepath = props.getProperty(name);;
        return filepath;
    }

    /**
     * 全为空
     * @param objects
     * @return
     */
    public static boolean allIsNull(Object...objects){
        int count = 0;
        if (Objects.isNull(objects[1])){
            return true;
        }
        for (int i = 1; i < objects.length; i++) {
            if (Objects.nonNull(objects[i])){
                count++;
            }
        }
        return count == 0;
    }

    /**
     * 全不为空
     * @param objects
     * @return
     */
    public static boolean allNonNull(Object...objects){
        return !allIsNull(objects);
    }
}
