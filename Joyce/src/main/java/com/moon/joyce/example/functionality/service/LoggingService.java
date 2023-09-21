package com.moon.joyce.example.functionality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.functionality.entity.doma.Logging;

import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:09
 * @describe: 日志
 */
public interface LoggingService extends IService<Logging> {
    /**
     * 日志列表
     * @param logging
     * @return
     */
    List<Logging> getList(Logging logging);
    /**
     * 日志数量
     * @param logging
     * @return
     */
    long getTotal(Logging logging);


}
