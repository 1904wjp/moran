package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.mapper.ColumnsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/19
 */
@Service
public class ColumnsServiceImpl implements ColumnsService {
   @Autowired
   private JdbcTemplate jdbcTemplate;
   @Autowired
   private ColumnsMapper columnsMapper;
    @Override
    public List<Column> getColumns(String tableName, String dbName) {
        //为了提高效率，先检测数据库和数据表
        Column table = columnsMapper.getOne(tableName, dbName);
        if (Objects.isNull(table)){
            return null;
        }
        return columnsMapper.getColumns(tableName,dbName);
    }

    @Override
    public Column getTable(String tableName, String dbName) {
        return columnsMapper.getOne(tableName, dbName);
    }

    @Override
    public List<Column> selectAllTables(String databaseName) {
        return columnsMapper.getAllTables(databaseName);
    }

    @Override
    public void execute(List<String> sqls) {
        for (String sql : sqls) {
            jdbcTemplate.execute(sql);
        }
    }


}
