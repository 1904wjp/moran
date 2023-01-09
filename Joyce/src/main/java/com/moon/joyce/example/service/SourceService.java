package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.Source;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 * 资源服务接口
 */

public interface SourceService extends IService<Source> {
    /**
     * 获取所有资源列表
     * @param source
     * @return
     */
    List<Source> getList(Source source);
    /**
     * 获取所有资源数量
     * @param source
     * @return
     */
    long getCount(Source source);

    /**
     * 查询单个资源
     * @param source
     * @return
     */
    Source getOne(Source source);

    /**
     * 获取视频信息
     * @param id
     * @return
     */
    Source getVideoInfo(Long id);

    /**
     * 根据id查询集合
     * @param ids
     * @param sessionUserId
     * @return
     */
    List<Source> getByIds(List<String> ids);
}
