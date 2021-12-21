package com.moon.joyce;



import com.moon.joyce.dataSource.DynamicDataSource;
import com.moon.joyce.example.functionality.entity.DataSource;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JoyceApplicationTests {
    @Autowired
    private DbBaseSettingService dbBaseSettingService;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Test
    public void contextLoads() {
        DbBaseSetting dbBaseSetting1 = new DbBaseSetting();
        dbBaseSetting1.setUserId(1L);
        List<DbBaseSetting> dbBaseSettings = dbBaseSettingService.getDbBaseSettings(dbBaseSetting1);
        for (DbBaseSetting dbBaseSetting : dbBaseSettings) {
            DataSource dataSource = new DataSource(dbBaseSetting.getDataSourceName(), dbBaseSetting.getUrl(), dbBaseSetting.getUsername(), dbBaseSetting.getPassword(), dbBaseSetting.getTempCode(), dbBaseSetting.getDatabaseType(), dbBaseSetting.getDriverName());
            dynamicDataSource.createDataSourceBySource(dataSource);
        }
    }


}
