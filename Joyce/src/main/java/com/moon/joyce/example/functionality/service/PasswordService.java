package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.WebEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web实体类
 */
@Service
public interface PasswordService extends IService<Password> {

    PageVo getList(Password password);

    int getCount(Password password);

    Password getOne(Password password);
}
