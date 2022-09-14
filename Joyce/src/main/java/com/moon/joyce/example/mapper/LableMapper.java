package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * web实体类mapper层
 */
@Mapper
public interface LableMapper extends BaseMapper<Lable> {
    List<Lable> getList(DictAndLable dictAndLable);
}
