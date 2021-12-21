package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.DataSource;
import com.moon.joyce.example.entity.DbBaseSetting;

import com.moon.joyce.example.functionality.service.DataSourceService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.mapper.DbBaseSettingMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据源service实现类
 */
@Service
public class DbBaseSettingServiceImpl extends ServiceImpl<DbBaseSettingMapper, DbBaseSetting> implements DbBaseSettingService {
    @Autowired
    private DbBaseSettingMapper dbBaseSettingMapper;
    @Autowired
    private DataSourceService dataSourceService;
    @Override
    public DbBaseSetting getDbBaseSetting(DbBaseSetting dbBaseSetting) {
        QueryWrapper<DbBaseSetting> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(dbBaseSetting.getId())){
            wrapper.eq("id",dbBaseSetting.getId());
        }
        if (StringUtils.isNoneBlank(dbBaseSetting.getDataSourceName())){
            wrapper.eq("data_source_name",dbBaseSetting.getDataSourceName());
        }
        if (StringUtils.isNoneBlank(dbBaseSetting.getUrl())){
            wrapper.eq("url",dbBaseSetting.getUrl());
        }
        if (StringUtils.isNoneBlank(dbBaseSetting.getPassword())){
            wrapper.eq("password",dbBaseSetting.getPassword());
        }
        if (Objects.nonNull(dbBaseSetting.getUserId())){
            wrapper.eq("user_id",dbBaseSetting.getUserId());
        }
        if (StringUtils.isNoneBlank(dbBaseSetting.getDatabaseType())){
            wrapper.eq("database_type",dbBaseSetting.getDatabaseType());
        }
        if (Objects.nonNull(dbBaseSetting.getApplyStatus())){
            wrapper.eq("apply_status",dbBaseSetting.getApplyStatus());
        }
        if (StringUtils.isNoneBlank(dbBaseSetting.getDriverName())){
            wrapper.eq("driver_name",dbBaseSetting.getDriverName());
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<DbBaseSetting> getDbBaseSettings(DbBaseSetting dbBaseSetting) {
        return dbBaseSettingMapper.getDbBaseSettings(dbBaseSetting);
    }

    @Override
    public int  getDbBaseSettingCount(DbBaseSetting dbBaseSetting) {
        return dbBaseSettingMapper.getCount(dbBaseSetting);
    }

    @Override
    public int getCount(DbBaseSetting dbBaseSetting,String type) {
        QueryWrapper<DbBaseSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",dbBaseSetting.getUserId());
        if (Constant.DATA_TYPE_UNIQUE_NAME.equals(type)){
            wrapper.eq("data_source_name",dbBaseSetting.getDataSourceName());
            return baseMapper.selectCount(wrapper);
        }
        if (Constant.DATA_TYPE_UNIQUE.equals(type)){
            wrapper.eq("driver_name",dbBaseSetting.getDriverName());
            wrapper.eq("username",dbBaseSetting.getUsername());
            wrapper.eq("password",dbBaseSetting.getPassword());
            wrapper.eq("database_type",dbBaseSetting.getDatabaseType());
            return baseMapper.selectCount(wrapper);
        }
        wrapper.eq("data_source_name",dbBaseSetting.getDataSourceName());
        wrapper.eq("driver_name",dbBaseSetting.getDriverName());
        wrapper.eq("username",dbBaseSetting.getUsername());
        wrapper.eq("password",dbBaseSetting.getPassword());
        wrapper.eq("database_type",dbBaseSetting.getDatabaseType());
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean switchDataSource( DbBaseSetting dbBaseSetting, String type) {
        if (Objects.isNull(dbBaseSetting)){
            return false;
        }
        DataSource dataSource = new DataSource(dbBaseSetting.getDataSourceName(), dbBaseSetting.getUrl(), dbBaseSetting.getUsername(), dbBaseSetting.getPassword(), dbBaseSetting.getTempCode(), dbBaseSetting.getDatabaseType(), dbBaseSetting.getDriverName());

        if (Constant.TEST_DATASOURCE.equals(type)){
            return dataSourceService.testDateSource(dataSource);
        }
        if (Constant.CREATE_DATASOURCE.equals(type)){
            return dataSourceService.changeDataSource(dbBaseSetting);
        }
        if (Constant.REMOVE_DATASOURCE.equals(type)){
            return dataSourceService.deleteDateSource(dataSource.getDataSourceName());
        }
        return false;
    }

    @Override
    public int updateRetire(Long userId) {
        DbBaseSetting dbBaseSetting = new DbBaseSetting();
        dbBaseSetting.setApplyStatus(0);
        UpdateWrapper<DbBaseSetting> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id",userId);
        return baseMapper.update(dbBaseSetting,wrapper);
    }

    @Override
    public DbBaseSetting getMain(DbBaseSetting dbBaseSetting) {
        QueryWrapper<DbBaseSetting> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(dbBaseSetting.getDataSourceName())){
            wrapper.eq("data_source_name",dbBaseSetting.getDataSourceName());
        }
        if (Objects.nonNull(dbBaseSetting.getUserId())){
            wrapper.eq("user_id",dbBaseSetting.getUserId());
        }
        if (Objects.nonNull(dbBaseSetting.getApplyStatus())){
            wrapper.eq("apply_status",dbBaseSetting.getApplyStatus());
        }
        return baseMapper.selectOne(wrapper);
    }


}
