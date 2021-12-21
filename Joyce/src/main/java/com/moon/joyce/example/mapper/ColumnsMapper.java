package com.moon.joyce.example.mapper;

import com.moon.joyce.example.functionality.entity.Column;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ColumnsMapper {
    /**
     * 查询所有列
     * @param tableName
     * @return
     */
    List<Column> getColumns(@Param("tableName") String tableName, @Param("dbName") String dbName);

    /**
     * 查询详细表信息
     * @param tableName
     * @return
     */
    Column getOne(@Param("tableName") String tableName, @Param("dbName") String dbName);
    /**
     * 查询所有表信息
     * @param databaseName
     * @return
     */
    List<Column> getAllTables(String databaseName);
}
