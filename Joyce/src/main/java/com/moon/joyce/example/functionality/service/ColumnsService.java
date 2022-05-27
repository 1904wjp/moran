package com.moon.joyce.example.functionality.service;

import com.moon.joyce.example.functionality.entity.Column;


import java.util.List;
import java.util.Set;

public interface ColumnsService {
    /**
     * 获取所有列
     * @param tableName
     * @param dbName
     * @return
     */
    List<Column> getColumns(String tableName, String dbName);

    /**
     * 获得表名和注释
     * @param tableName
     * @param dbName
     * @return
     */
    Column getTable(String tableName, String dbName);

    /**
     * 根据数据库名称查询所有的表名
     * @param databaseName
     * @return
     */
    List<Column> selectAllTables(String databaseName);

    /**
     * 自动创建表sql
     * @param set
     */
    void execute(Set<String> set);
}
