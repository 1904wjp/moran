package com.moon.joyce.example.functionality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.functionality.entity.doma.Accounting;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:09
 * @describe:
 */
public interface AccountingService extends IService<Accounting> {
    /**
     * 记帐户的数据列表
     * @param accounting
     * @return
     */
    List<Accounting> getList(Accounting accounting);
    /**
     * 记帐户的数据数量
     * @param accounting
     * @return
     */
    long getTotal(Accounting accounting);

    /**
     * 根据时间获取数据集
     * @param type
     * @param date
     * @return
     */
    List<Accounting> getDataByDate(String type, Date date,Long userId);
}
