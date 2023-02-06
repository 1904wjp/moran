package com.moon.joyce.example.functionality.service;

import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Column;


import java.util.List;
import java.util.Map;
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
     * @param list
     */
    void execute(List<String> list);

    /**
     * 执行sql
     * @param sql
     * @return
     */
    Object execute(String sql);
    /**
     *  查询表数据
     * @param tableName
     * @return
     */
    List<Map<String,Object>> getMapTableData(String tableName,String dbName,int pageNumber,int offset);

    /**
     * 获取表数据数量
     * @param tableName
     * @param dbName
     * @return
     */
    int getMapTableDataCount(String tableName, String dbName);

    /**
     * 根据set获取表信息
     * @param tableSet
     * @param dbName
     * @return
     */
    Map<String, List<Column>> getTableInfoBySet(Set<String> tableSet, String dbName);

    /**
     * 获取数据库名称
     * @return
     */
    List<String> getDataBaseNames();

    /**
     * 获取表信息
     * @param tableName
     * @param dbName
     * @param pageNumber
     * @param offset
     * @return
     */
    PageVo getMapTableDataPage(String tableName, String dbName, int pageNumber, int offset);

    List<Column> selectAllTables2(String databaseName);
}
