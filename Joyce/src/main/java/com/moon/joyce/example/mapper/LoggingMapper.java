package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.doma.Logging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:15
 * @describe: 日志
 */
@Mapper
public interface LoggingMapper extends BaseMapper<Logging> {
    /**
     * 日志集合
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

    /**
     * 真删除id集合的行
     * @param ids
     * @return
     */
    int deleteByIds(List<Long> ids);
}
