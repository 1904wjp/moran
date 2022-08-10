package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.DayTask;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
public interface DayTaskMapper  extends BaseMapper<DayTask>{
    /**
     * 获取每日任务集合
     * @param dayTask
     * @return
     */
    List<DayTask> selectList(DayTask dayTask);
    /**
     * 获取每日任务集合条数
     * @param dayTask
     * @return
     */
    int selectCount(DayTask dayTask);
    /**
     * 获取最后一条数据
     */
    DayTask getLastData(Long id);
}
