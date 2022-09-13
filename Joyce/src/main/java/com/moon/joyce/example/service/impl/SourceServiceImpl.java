package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
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
        QueryWrapper<Source> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_flag",source.getDeleteFlag());
        if (Objects.nonNull(source.getApplyStatus())){
            wrapper.eq("apply_status",source.getApplyStatus());
        }
        if (StringUtils.isNoneBlank(source.getSourceName())){
            wrapper.eq("source_name",source.getSourceName());
        }
        if (Objects.nonNull(source.getUserId())){
            wrapper.eq("user_id",source.getUserId());
        }
        if (Objects.nonNull(source.getType())){
            wrapper.eq("type",source.getType());
        }
        return baseMapper.selectOne(wrapper);
    }


}
