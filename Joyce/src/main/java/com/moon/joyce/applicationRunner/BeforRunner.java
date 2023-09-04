package com.moon.joyce.applicationRunner;


import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.example.functionality.entity.doma.Column;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.commons.factory.demo.AutoCreateTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 1:11
 * @describe: 启动时运行自动创建表
 */
@Component
public class BeforRunner implements ApplicationRunner {
    @Value("${auto.entity.package}")
    private String ps;
    @Value("${auto.source.dbName}")
    private String dbName;
    @Value("${auto.source.delPath}")
    private String path;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ColumnsService columnsService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> dataBaseNames = columnsService.getDataBaseNames();
        logger.info("-----------{}",dataBaseNames.toString());
        try {
            //实例化创建sql自动建表工厂
            AutoCreateTableFactory factory = AutoCreateTableFactory.getInstance();
            //传入路径，实例化数据
            factory.initData(ps);
            //获取需要执行名称容器
            Map<String, Set<String>> tableSet = factory.getTableSet();
            //获取执行的表对象容器
            Map<String, List<Column>> map = columnsService.getTableInfoBySet(tableSet.get(TableStrategy.ADD.getCode().toString()), dbName);
            //强制建表的sql容器
            Set<String> forceSet = tableSet.get(TableStrategy.FORCE.getCode().toString());
            for (String tableName : forceSet) {
                List<Column> columns = columnsService.getColumns(tableName, dbName);
                if (Objects.isNull(columns)){
                    continue;
                }
                Column table = null;
                if (columns.isEmpty()){
                    table = columnsService.getTable(tableName, dbName);
                }else {
                    table = columns.get(0);
                }
                List<Map<String, Object>> mapTableData = columnsService.getMapTableData(tableName, dbName, 0, 0);
                //强制建表，若有存在的表，需要导出文件后删除sql
                factory.exportSqlFile(columns,mapTableData,table,path);
            }
            //获取最终的sql
            List<String> sqls = factory.getSqls(map);
            //执行sql
            columnsService.execute(sqls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
