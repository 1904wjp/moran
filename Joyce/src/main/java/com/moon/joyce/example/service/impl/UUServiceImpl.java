package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.UU;
import com.moon.joyce.example.mapper.UUMapper;
import com.moon.joyce.example.service.CommonService;
import com.moon.joyce.example.service.UUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Joyce
 * @since 2021-09-25
 *  用户与用户关系的服务实现类
 */
@Service
@RedisValueComponet("UUService")
public class UUServiceImpl extends ServiceImpl<UUMapper, UU> implements UUService , CommonService {
    @Autowired
    private UUMapper uuMapper;
    @Override
    public List<Long> getList(String type, Long userId) {
        return uuMapper.getListByType(type,userId);
    }

    @Override
    public int sendArticleFriendApplication(UU uu) {
        QueryWrapper<UU> wrapper = new QueryWrapper<>();
        wrapper.eq("usera_id",uu.getUserAId());
        wrapper.eq("userb_id",uu.getUserBId());
        UU dbUu = baseMapper.selectOne(wrapper);
        if (Objects.nonNull(dbUu)){
            return -1;
        }
        uu.setDeleteFlag(Constant.UNDELETE_STATUS);
        return baseMapper.insert(uu);
    }

    @Override
    public UU getOne(UU uu) {
        QueryWrapper<UU> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(uu.getUserAId())){
            wrapper.eq("user_a_id",uu.getUserAId());
        }
        if (Objects.nonNull(uu.getUserBId())){
            wrapper.eq("user_b_id",uu.getUserBId());
        }
        return baseMapper.selectOne(wrapper);
    }




}
