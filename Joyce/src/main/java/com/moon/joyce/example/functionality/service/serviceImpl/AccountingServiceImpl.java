package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.doma.Accounting;
import com.moon.joyce.example.functionality.service.AccountingService;
import com.moon.joyce.example.mapper.AccountingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:15
 * @describe:
 */
@Service
public class AccountingServiceImpl extends ServiceImpl<AccountingMapper, Accounting> implements AccountingService {
    @Autowired
    private AccountingMapper accountingMapper;

    @Override
    public List<Accounting> getList(Accounting accounting) {
        return accountingMapper.getList(accounting);
    }

    @Override
    public long getTotal(Accounting accounting) {
        return accountingMapper.getTotal(accounting);
    }

    @Override
    public List<Accounting> getDataByDate(String type, Date date,Long userId) {
        return accountingMapper.getData(type,date,userId);
    }
}
