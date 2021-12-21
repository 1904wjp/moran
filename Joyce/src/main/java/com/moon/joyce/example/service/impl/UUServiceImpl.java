package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.UU;
import com.moon.joyce.example.mapper.UUMapper;
import com.moon.joyce.example.service.UUService;
import joyce.example.entity.UserType;
import joyce.example.mapper.UserTypeMapper;
import joyce.example.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Joyce
 * @since 2021-09-25
 *  用户与用户关系的服务实现类
 */
@Service
public class UUServiceImpl extends ServiceImpl<UUMapper, UU> implements UUService {
    @Autowired
    private UUMapper uuMapper;
    @Override
    public List<Long> getList(String type, Long userId) {
        return uuMapper.getListByType(type,userId);
    }
}
