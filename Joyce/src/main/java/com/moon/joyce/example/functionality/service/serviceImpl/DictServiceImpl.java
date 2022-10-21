package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.joyce.entity.Node;
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
        if (Objects.nonNull(dict.getCode())){
            wrapper.eq("code",dict.getCode());
        }
        if (Objects.nonNull(dict.getName())){
            wrapper.eq("name",dict.getName());
        }
        List<Dict> dicts = baseMapper.selectList(wrapper);
        List<Dict> list = new ArrayList<>();
        if (!dicts.isEmpty()){
            for (Dict d : dicts) {
                if (Objects.isNull(d)){
                    continue;
                }
                d.setLables(lableService.getlables(new DictAndLable(d.getId(),null)));
                list.add(d);
            }
        }
        return list;
    }

    @Override
    public List<Node<Dict>> getDictNode(Dict dict) {
        List<Dict> dicts = getDicts(dict);
        List<Node<Dict>> nodes = new ArrayList<>();
        for (Dict d : dicts) {
            Node<Dict> head = new Node<>();
            Dict current = d;
            while (current!=null){
                head.add(current);
                Dict dt= new Dict();
                dt.setCode(current.getName());
                current = getOne(dt);
            }
            nodes.add(head);
        }
        return nodes;
    }

    @Override
    public Dict getOne(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(dict.getCode())){
            wrapper.eq("code",dict.getCode());
        }
        if (Objects.nonNull(dict.getName())){
            wrapper.eq("name",dict.getName());
        }
        return baseMapper.selectOne(wrapper);
    }
}
