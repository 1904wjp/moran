package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.entity.UU;

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

    /**
     * 发送好友请求
     * @param uu
     * @return
     */
    int sendArticleFriendApplication(UU uu);


    /**
     * 查询某一个用户与用户之间的关系
     * @param uu
     * @return
     */
    UU getOne(UU uu);
}
