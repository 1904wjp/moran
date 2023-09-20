package com.moon.joyce.commons.joyce;


import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.utils.JSONUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.controller.TestController;
import com.moon.joyce.example.entity.doma.User;
import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
