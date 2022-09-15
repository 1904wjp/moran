package com.moon.joyce.commons.utils;

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
}
