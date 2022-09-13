package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.doma.WebEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * web实体类mapper层
 */
@Mapper
public interface WebEntityMapper extends BaseMapper<WebEntity> {

}
