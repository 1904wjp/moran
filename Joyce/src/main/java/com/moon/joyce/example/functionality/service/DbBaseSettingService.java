package com.moon.joyce.example.functionality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.DbBaseSetting;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源
 */
@Service
public interface DbBaseSettingService extends IService<DbBaseSetting> {
    /**
     * 获对用的封装的数据源
     * @param dbBaseSetting
     * @return
     */
    DbBaseSetting getDbBaseSetting(DbBaseSetting dbBaseSetting);

    /**
     * 获取所有封装的数据源
     * @param dbBaseSetting
     * @return
     */
    List<DbBaseSetting> getDbBaseSettings(DbBaseSetting dbBaseSetting);


    /**
     * 获取所有封装的数据源的数目
     * @param dbBaseSetting
     * @return
     */
    long getDbBaseSettingCount(DbBaseSetting dbBaseSetting);

    /**
     * 查询条件数量
     * @param dbBaseSetting
     * @return
     */
    int getCount(DbBaseSetting dbBaseSetting,String type);

    /**
     * 调用数据源方法
     * @param dbBaseSetting
     * @param type
     * @return
     */
    boolean switchDataSource(DbBaseSetting dbBaseSetting,String type);

    /**
     * 将所有对象设置为非应用
     * @return
     */
    int updateRetire(Long userId);

    /**
     * 根据当前登录人和数据源名称获取数据源信息
     * @param dbBaseSetting
     * @return
     */
    DbBaseSetting getMain(DbBaseSetting dbBaseSetting);

}
