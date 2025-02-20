package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.doma.Source;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Mapper
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
    long selectCount(Source source);

    /**
     * 根据图片获取视频信息
     * @param id
     * @return
     */
    Source getVideoByVPicId(Long id);

    /**
     * 根据ids查询集合
     * @param ids
     * @return
     */
    List<Source> getByIds(List<String> ids);
}
