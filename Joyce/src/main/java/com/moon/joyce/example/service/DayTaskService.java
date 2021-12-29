package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.DayTask;
import joyce.example.entity.UserType;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    /**
     * 查找最后一条数据
     */
    DayTask getLastData(Long id);

    /**
     * 导处excel表
     * @param list
     * @return
     */
    boolean exportTable(List<DayTask> list, HttpServletResponse response);

    /**
     * 导处excel表（分组）
     * @param map
     * @param response
     * @return
     */
    boolean exportTableByManySheet(Map<String, List<DayTask>> map, HttpServletResponse response);
}
