package com.moon.joyce.example.service.impl;

import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.mapper.UserTypeMapper;
import com.moon.joyce.example.service.CommonService;
import joyce.example.entity.UserType;

import joyce.example.service.UserTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Service
@RedisValueComponet("UserTypeService")
public class UserTypeServiceImpl extends ServiceImpl<UserTypeMapper, UserType> implements UserTypeService , CommonService {

}
