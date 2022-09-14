package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.functionality.entity.doma.DictAndLable;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web实体类
 */

@Service
public interface LableService extends IService<Lable> {
    List<Lable> getlables(DictAndLable dictAndLable);
}
