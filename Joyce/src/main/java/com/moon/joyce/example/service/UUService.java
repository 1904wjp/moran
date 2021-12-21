package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.UU;
import joyce.example.entity.UserType;

import java.util.List;

/**
 *
 * @author Joyce
 * @since 2021-09-25
 * 用户与用户关系的服务接口
 */
public interface UUService extends IService<UU> {
    /**
     * 获取关系用户id集合
     * @param type
     * @param userId
     * @return
     */
     List<Long> getList(String type,Long userId);
}
