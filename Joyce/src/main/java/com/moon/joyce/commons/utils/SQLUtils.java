package com.moon.joyce.commons.utils;

import com.moon.joyce.example.functionality.entity.JoyceException;

import java.io.Serializable;

/**
 * @author Xing Dao Rong
 * @date 2021/9/1 17:05
 * @desc sql工具类
 */
public class SQLUtils implements Serializable {
    private static final long serialVersionUID = -726828122220412530L;
    private SQLUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
}
