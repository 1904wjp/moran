package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.dataSource.DbContextHolder;
import com.moon.joyce.dataSource.DynamicDataSource;
import com.moon.joyce.example.functionality.entity.doma.DataSource;
import com.moon.joyce.example.entity.doma.DbBaseSetting;
import com.moon.joyce.example.functionality.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/25
 * 数据源服务实现类
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DynamicDataSource dynamicDataSource;


    /**
     * 切换数据源
     * @param dbBaseSetting
     * @return
     */
    @Override
    public boolean changeDataSource(DbBaseSetting dbBaseSetting) {

           DataSource dataSource = new DataSource(
                   dbBaseSetting.getDataSourceName(),
                   dbBaseSetting.getUrl(),
                   dbBaseSetting.getUsername(),
                   dbBaseSetting.getPassword(),
                   dbBaseSetting.getTempCode(),
                   dbBaseSetting.getDatabaseType(),
                   dbBaseSetting.getDriverName());
                //创建并且检查数据源连接.若存在则不需要创建
                try {
                    dynamicDataSource.createDataSourceWithCheck(dataSource);
                } catch (Exception e) {
                    logger.info("数据源创建失败,Exception:"+e);
                    return false;
                }
                //切换到改数据源
                DbContextHolder.setDataSource(dataSource.getDataSourceName());
                 logger.info("数据源确定完成:"+dbBaseSetting.getDataSourceName());
                return true;

    }

     @Override
     public boolean testDateSource(DataSource dataSource){
         logger.info("启动测试指定数据源:"+dataSource.getDataSourceName());
         return dynamicDataSource.testDatasource(dataSource.getDataSourceName(), dataSource.getDriver(), dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassWord());
     }

    @Override
    public boolean deleteDateSource(String datasourceName) {
        logger.info("启动删除指定数据源:"+datasourceName);
        return dynamicDataSource.delDatasources(datasourceName);
    }
}
