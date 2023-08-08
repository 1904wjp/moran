package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.doma.Source;
import com.moon.joyce.example.mapper.SourceMapper;
import com.moon.joyce.example.service.CommonService;
import com.moon.joyce.example.service.SourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
@RedisValueComponet("SourceService")
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements SourceService , CommonService {
    ObjectWrapper<Source> objectWrapper = new ObjectWrapper<>();
    @Autowired
    private SourceMapper sourceMapper;
    @Override
    public List<Source> getList(Source source) {
        return sourceMapper.selectList(source);
    }

    @Override
    public long getCount(Source source) {
        return sourceMapper.selectCount(source);
    }

    @Override
    public Source getOne(Source source) {
        objectWrapper.flash();
        if (Objects.nonNull(source.getApplyStatus())){
            objectWrapper.wrapper.eq(Source::getApplyStatus,source.getApplyStatus());
        }
        if (StringUtils.isNoneBlank(source.getSourceName())){
            objectWrapper.wrapper.eq(Source::getSourceName,source.getSourceName());
        }
        if (Objects.nonNull(source.getUserId())){
            objectWrapper.wrapper.eq(Source::getUserId,source.getUserId());
        }
        if (Objects.nonNull(source.getType())){
            objectWrapper.wrapper.eq(Source::getType,source.getType());
        }
        return baseMapper.selectOne(objectWrapper.wrapperi);
    }

    @Override
    public Source getVideoInfo(Long id) {
        return sourceMapper.getVideoByVPicId(id);
    }

    @Override
    public List<Source> getByIds(List<String> ids) {
        return sourceMapper.getByIds(ids);
    }


}
