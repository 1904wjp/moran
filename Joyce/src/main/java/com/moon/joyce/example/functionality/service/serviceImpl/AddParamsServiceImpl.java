package com.moon.joyce.example.functionality.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.utils.CommonUtils;
import com.moon.joyce.example.entity.base.entity.doma.AddParams;
import com.moon.joyce.example.functionality.service.AddParamsService;
import com.moon.joyce.example.mapper.AddParamsDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/11/02-- 14:06
 * @describe:
 */
@Service
public class AddParamsServiceImpl extends ServiceImpl<AddParamsDao,AddParams> implements AddParamsService {
    @Override
    public AddParams getParams(Long id) {
        AddParams addParams = baseMapper.selectById(id);
        if (CommonUtils.allIsNull(addParams,addParams.getParams())){
            return null;
        }
        addParams.setMap( JSON.parseObject(addParams.getParams(), Map.class));
        return addParams;
    }
}
