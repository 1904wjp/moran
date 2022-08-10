package com.moon.joyce.applicationRunner;


import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.example.functionality.entity.Column;
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
 * @describe: 启动时运行自动床表
 */
@Component
public class AutoCreateTableRunner implements ApplicationRunner {
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
            AutoCreateTableFactory factory = AutoCreateTableFactory.getInstance();
            factory.init(ps);
            Map<String, Set<String>> tableSet = factory.getTableSet();
            Map<String, List<Column>> map = columnsService.getTableInfoBySet(tableSet.get(TableStrategy.ADD.getCode().toString()), dbName);
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
                factory.exportSqlFile(columns,mapTableData,table,path);
            }
            List<String> sqls = factory.getSqls(map);
            columnsService.execute(sqls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
