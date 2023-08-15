package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import com.moon.joyce.example.functionality.service.LableService;
import com.moon.joyce.example.mapper.LableMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public Lable getOne(Lable lable) {
        QueryWrapper<Lable> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(lable.getCode())){
            wrapper.lambda().eq(Lable::getCode,lable.getCode());
        }
        if (StringUtils.isNoneBlank(lable.getName())){
            wrapper.lambda().eq(Lable::getName,lable.getName());
        }
        if (StringUtils.isNoneBlank(lable.getType())){
            wrapper.lambda().eq(Lable::getType,lable.getType());
        }
        if (StringUtils.isNoneBlank(lable.getCreateBy())){
            wrapper.lambda().eq(Lable::getCreateBy,lable.getCreateBy());
        }
        if (StringUtils.isNoneBlank(lable.getUpdateBy())){
            wrapper.lambda().eq(Lable::getUpdateBy,lable.getUpdateBy());
        }
        if (Objects.nonNull(lable.getCreateIds())){
            wrapper.lambda().eq(Lable::getCreateIds,lable.getCreateIds());
        }
        if (Objects.nonNull(lable.getUpdateIds())){
            wrapper.lambda().eq(Lable::getUpdateIds,lable.getUpdateIds());
        }
        wrapper.lambda().eq(Lable::getDeleteFlag, Constant.UNDELETE_STATUS);
        return lableMapper.selectOne(wrapper);
    }
}
