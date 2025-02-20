package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.PayConfig;
import org.springframework.stereotype.Service;

/**
 * web实体类
 */

@Service
public interface PayConfigService extends IService<PayConfig> {

    PageVo<PayConfig> getPage(PayConfig payConfig);

    PayConfig getOne(PayConfig payConfig);
}
