package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.doma.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * web实体类mapper层
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
