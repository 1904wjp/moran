package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.mapper.SourceMapper;
import com.moon.joyce.example.service.SourceService;
import joyce.example.entity.UserType;
import joyce.example.mapper.UserTypeMapper;
import joyce.example.service.UserTypeService;
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

    @Override
    public Source getOne(Source source) {
        QueryWrapper<Source> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_flag",source.getDeleteFlag());
        if (Objects.isNull(source.getApplyStatus())){
            wrapper.eq("apply_status",source.getApplyStatus());
        }
        if (StringUtils.isNoneBlank(source.getSourceName())){
            wrapper.eq("source_name",source.getSourceName());
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int setMain(Source source) {
        QueryWrapper<Source> wrapper = new QueryWrapper<>();
        wrapper.eq("type",source.getType());
        wrapper.eq("apply_status",Constant.APPLY_STATUS);
        wrapper.eq("delete_flag", Constant.UNDELETE_STATUS);
        Source dbSource = baseMapper.selectOne(wrapper);
        if (Objects.nonNull(dbSource)){
            UpdateWrapper<Source> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",dbSource.getId());
            updateWrapper.set("apply_status",Constant.SPARE_STATUS);
        }
        UpdateWrapper<Source> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",source.getId());
        updateWrapper.set("apply_status",Constant.APPLY_STATUS);
        return baseMapper.update(source,updateWrapper);
    }
}
