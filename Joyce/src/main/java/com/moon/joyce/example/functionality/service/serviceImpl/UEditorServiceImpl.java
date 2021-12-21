package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.WebEntity;
import com.moon.joyce.example.functionality.service.UEditorService;
import com.moon.joyce.example.functionality.service.WebEntityService;
import com.moon.joyce.example.mapper.UEditorMapper;
import com.moon.joyce.example.mapper.WebEntityMapper;
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
public class UEditorServiceImpl extends ServiceImpl<UEditorMapper, Article> implements UEditorService {
    @Autowired
     private UEditorMapper uEditorMapper;
    @Override
    public List<Article> getList(Article article ,List<Long> idList) {
        return uEditorMapper.selectList(article,idList);
    }

    @Override
    public int getTotal(Article article,List<Long> idList) {
        return uEditorMapper.getTotal(article,idList);
    }
}
