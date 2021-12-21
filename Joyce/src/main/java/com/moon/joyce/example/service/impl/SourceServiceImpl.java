package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.mapper.SourceMapper;
import com.moon.joyce.example.service.SourceService;
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
 * 资源服务实现类
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements SourceService {
    @Autowired
    private SourceMapper sourceMapper;
    @Override
    public List<Source> getList(Source source) {
        return sourceMapper.selectList(source);
    }

    @Override
    public int getCount(Source source) {
        return sourceMapper.selectCount(source);
    }
}
