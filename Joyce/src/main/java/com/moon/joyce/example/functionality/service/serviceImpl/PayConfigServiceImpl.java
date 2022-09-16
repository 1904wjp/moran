package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import com.moon.joyce.example.functionality.entity.doma.PayConfig;
import com.moon.joyce.example.functionality.service.LableService;
import com.moon.joyce.example.functionality.service.PayConfigService;
import com.moon.joyce.example.mapper.LableMapper;
import com.moon.joyce.example.mapper.PayConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */

@Service
public class PayConfigServiceImpl extends ServiceImpl<PayConfigMapper, PayConfig> implements PayConfigService {

    @Override
    public PageVo<PayConfig> getPage(PayConfig payConfig) {
        QueryWrapper<PayConfig> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(payConfig.getSearch())){
            String search = payConfig.getSearch();
            wrapper.lambda().eq(PayConfig::getAppid,search)
                    .or().eq(PayConfig::getPid,search);
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page page = Page.getPage(payConfig);
        return new PageVo<>(baseMapper.selectPage(page,wrapper));
    }

    @Override
    public PayConfig getOne(PayConfig payConfig) {
        QueryWrapper<PayConfig> payConfigQueryWrapper = new QueryWrapper<>();
        payConfigQueryWrapper.lambda().eq(PayConfig::getStatus,payConfig.getStatus());
        payConfigQueryWrapper.lambda().eq(PayConfig::getDeleteFlag,payConfig.getDeleteFlag());
        payConfigQueryWrapper.lambda().eq(PayConfig::getCreateIds,payConfig.getCreateIds());
        return getOne(payConfigQueryWrapper);
    }
}
