package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.connection.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * web实体类mapper层
 */
@Mapper
public interface LableMapper extends BaseMapper<Lable> {
    /**
     * 查询所有的标签
     * @param dictAndLable
     * @return
     */
    List<Lable> getList(DictAndLable dictAndLable);
}
