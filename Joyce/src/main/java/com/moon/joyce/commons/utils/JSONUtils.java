package com.moon.joyce.commons.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/10-- 13:59
 * @describe:
 */
public class JSONUtils extends com.alibaba.druid.support.json.JSONUtils {
    /**
     * json转map
     * @param jsonObject
     * @param map
     * @return
     */
    public static Map<String,Object> JSONObjectToMap(JSONObject jsonObject,Map<String,Object> map){
        JSONObjectToMap2(jsonObject,map);
        return map;
    }

    public static Map<String,Object> JSONObjectToMap(JSONObject jsonObject){
        HashMap<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }
    public static void JSONObjectToMap2(JSONObject jsonObject,Map<String,Object> map){
        if (map==null){
            map = new HashMap<>();
        }
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(),entry.getValue());
        }
    }

}
