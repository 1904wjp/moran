package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Column;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.mapper.ColumnsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/19
 */
@Service
public class ColumnsServiceImpl implements ColumnsService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        List<Column> columns = columnsMapper.getColumns(tableName, dbName);
        List<Column> list = new ArrayList<>();
        for (Column column : columns) {
            if (Objects.isNull(column.getColumnLength())){
                column.setColumnLength(0L);
            }
            list.add(column);
        }
        return list;
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

    @Override
    public List<Map<String,Object>> getMapTableData(String tableName,String dbName,int pageNumber,int offset) {
        return columnsMapper.getMapData(tableName, dbName,pageNumber,offset);
    }

    @Override
    public int getMapTableDataCount(String tableName, String dbName) {
        return columnsMapper.getCount(tableName,dbName);
    }

    @Override
    public Map<String, List<Column>> getTableInfoBySet(Set<String> tableSet,String dbName) {
        Map<String, List<Column>> map = new HashMap<>();
        for (String tableName : tableSet) {
            List<Column> columns = getColumns(tableName, dbName);
            if (Objects.nonNull(columns)&&!columns.isEmpty()){
                map.put(tableName,columns);
            }
        }
        return map;
    }

    @Override
    public List<String> getDataBaseNames() {
        return columnsMapper.getDataBases();
    }

    @Override
    public PageVo getMapTableDataPage(String tableName, String dbName, int pageNumber, int offset) {
        return new PageVo(columnsMapper.getMapData(tableName, dbName, pageNumber, offset),columnsMapper.getCount(tableName,dbName));
    }

    @Override
    public List<Column> selectAllTables2(String databaseName) {
        return columnsMapper.getAllTables2(databaseName);
    }


}
