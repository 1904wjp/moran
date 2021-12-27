package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.DayTask;
import joyce.example.entity.UserType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
public interface DayTaskService extends IService<DayTask> {
    /**
     * 获取每日看板集合
     * @param dayTask
     * @return
     */
    List<DayTask> getList(DayTask dayTask);
    /**
     * 获取每日看板集合条数
     * @param dayTask
     * @return
     */
    int getCount(DayTask dayTask);
}
