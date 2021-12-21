package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.WebEntity;
import com.moon.joyce.example.functionality.service.WebEntityService;
import com.moon.joyce.example.mapper.WebEntityMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */
@Service
public class WebEntityServiceImpl extends ServiceImpl<WebEntityMapper, WebEntity> implements WebEntityService {

}
