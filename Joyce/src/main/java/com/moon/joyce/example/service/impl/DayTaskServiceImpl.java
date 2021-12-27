package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.DayTask;
import com.moon.joyce.example.mapper.DayTaskMapper;
import com.moon.joyce.example.service.DayTaskService;
import joyce.example.entity.UserType;
import joyce.example.mapper.UserTypeMapper;
import joyce.example.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Service
public class DayTaskServiceImpl extends ServiceImpl<DayTaskMapper, DayTask> implements DayTaskService {
    @Autowired
    private DayTaskMapper dayTaskMapper;
    @Override
    public List<DayTask> getList(DayTask dayTask) {
        return dayTaskMapper.selectList(dayTask);
    }

    @Override
    public int getCount(DayTask dayTask) {
        return dayTaskMapper.selectCount(dayTask);
    }
}
