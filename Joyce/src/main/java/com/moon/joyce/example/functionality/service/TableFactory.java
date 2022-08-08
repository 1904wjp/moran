package com.moon.joyce.example.functionality.service;

import com.moon.joyce.example.functionality.entity.Column;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/17-- 9:27
 * @describe: 表工厂抽线类
 */
public interface TableFactory {
    /**
     * 获取工厂名称
     * @return
     */
    String getTableFactoryName();

    /**
     * 获取sql
     * @return
     */
    List<String> getSqls(Map<String,List<Column>> map);

    /**
     * 初始化
     * @param ps
     */
    void initData(String ps);
}
