package com.moon.joyce.example.mapper;

import com.moon.joyce.example.functionality.entity.doma.Column;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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


    /**
     * 查询所有表信息
     * @param databaseName
     * @return
     */
    List<Column> getAllTables2(String databaseName);


    /**
     * 查询特定表中的数据
     * @param tableName
     * @param dbName
     * @return
     */
    List<Map<String,Object>> getMapData(@Param("tableName") String tableName, @Param("dbName") String dbName,@Param("pageNumber")int pageNumber,@Param("offset")int offset);

    /**
     * 获取表数据数量
     * @param tableName
     * @param dbName
     * @return
     */
    int getCount(@Param("tableName") String tableName, @Param("dbName") String dbName);

    /**
     * 获取数据库名称
     * @return
     */
    @Select("SHOW DATABASES")
    List<String> getDataBases();
}
