package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.functionality.entity.doma.Dict;
import com.moon.joyce.example.functionality.entity.doma.WebEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web实体类
 */

@Service
public interface DictService extends IService<Dict> {

     List<Dict> getDicts(Dict dict);
}
