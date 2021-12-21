package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.Source;
import joyce.example.entity.UserType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
public interface SourceMapper extends BaseMapper<Source> {
    /**
     * 获取资源列表
     * @param source
     * @return
     */
    List<Source> selectList(Source source);

    /**
     * 获取资源数量
     * @param source
     * @return
     */
    int selectCount(Source source);
}
