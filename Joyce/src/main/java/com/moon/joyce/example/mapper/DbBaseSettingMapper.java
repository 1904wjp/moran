package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.PackageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DbBaseSettingMapper extends BaseMapper<DbBaseSetting> {
 /**
  * 查询数据源所有信息数据
  * @param dbBaseSetting
  * @return
  */
  List<DbBaseSetting> getDbBaseSettings(DbBaseSetting dbBaseSetting);
    /**
     * 查询数据源所有信息数据数目
     * @param dbBaseSetting
     * @return
     */
    int getCount(DbBaseSetting dbBaseSetting);
}
