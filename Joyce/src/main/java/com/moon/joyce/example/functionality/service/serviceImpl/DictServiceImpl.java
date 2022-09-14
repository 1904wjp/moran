package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Dict;
import com.moon.joyce.example.functionality.service.DictService;
import com.moon.joyce.example.functionality.service.LableService;
import com.moon.joyce.example.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Autowired
    private LableService lableService;
    @Override
    public List<Dict> getDicts(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("code",dict.getCode());
        wrapper.eq("name",dict.getName());
        List<Dict> dicts = baseMapper.selectList(wrapper);
        List<Dict> list = new ArrayList<>();
        if (!dicts.isEmpty()){
            for (Dict d : dicts) {
                if (Objects.isNull(d)){
                    continue;
                }
                d.setLables(lableService.getlables(new DictAndLable(d.getId(),null,d.getType())));
                list.add(d);
            }
        }
        return list;
    }
}
