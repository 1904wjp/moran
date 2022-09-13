package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.doma.DbBaseSetting;
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
    long getCount(DbBaseSetting dbBaseSetting);
}
