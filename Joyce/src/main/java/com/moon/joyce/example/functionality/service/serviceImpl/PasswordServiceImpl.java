package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.WebEntity;
import com.moon.joyce.example.functionality.service.PasswordService;
import com.moon.joyce.example.functionality.service.WebEntityService;
import com.moon.joyce.example.mapper.PasswordMapper;
import com.moon.joyce.example.mapper.WebEntityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */
@Service
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Password> implements PasswordService {

    @Override
    public List<Password> getList(Password password) {
        return null;
    }
}
