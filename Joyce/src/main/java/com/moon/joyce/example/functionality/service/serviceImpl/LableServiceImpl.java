package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import com.moon.joyce.example.functionality.service.LableService;
import com.moon.joyce.example.mapper.LableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */

@Service
public class LableServiceImpl extends ServiceImpl<LableMapper, Lable> implements LableService {
    @Autowired
    private LableMapper lableMapper;
    @Override
    public List<Lable> getlables(DictAndLable dictAndLable) {
        return lableMapper.getList(dictAndLable);
    }
}
