package com.moon.joyce.example.functionality.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.base.entity.doma.AddParams;

import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/11/02-- 14:02
 * @describe: 参数服务接口
 */
public interface AddParamsService extends IService<AddParams> {
    /**
     * 根据id参数
     * @param id
     * @return
     */
     AddParams getParams(Long id);



}
