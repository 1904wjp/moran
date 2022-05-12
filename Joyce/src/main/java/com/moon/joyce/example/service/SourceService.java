package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.entity.Source;
import joyce.example.entity.UserType;

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
    int getCount(Source source);

    /**
     * 查询单个资源
     * @param source
     * @return
     */
    Source getOne(Source source);


}
