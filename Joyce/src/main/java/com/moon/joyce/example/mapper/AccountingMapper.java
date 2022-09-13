package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.doma.Accounting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:15
 * @describe:
 */
@Mapper
public interface AccountingMapper extends BaseMapper<Accounting> {
    /**
     * 获取记账数据集合
     * @param accounting
     * @return
     */
    List<Accounting> getList(Accounting accounting);

    /**
     * 获取记账数量
     * @param accounting
     * @return
     */
    long getTotal(Accounting accounting);

    /**
     * 根据类型和日期获取数据
     * @param type
     * @param date
     * @return
     */
    List<Accounting> getData(@Param("type") String type,@Param("date") Date date,@Param("userId") Long userId);
}
