package com.moon.joyce.example.functionality.service;

import com.moon.joyce.example.entity.doma.DbBaseSetting;
import com.moon.joyce.example.functionality.entity.doma.DataSource;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/25
 * 数据源服务层
 */
public interface DataSourceService {
    /**
     * 切换数据源
     * @param dbBaseSetting
     * @return
     */
    boolean changeDataSource(DbBaseSetting dbBaseSetting);

    /**
     * 测试数据源
     * @param dataSource
     * @return
     */
    boolean testDateSource(DataSource dataSource);

    /**
     * 删除数据源
     * @param dataSourceName
     * @return
     */
    boolean deleteDateSource(String dataSourceName);
}
