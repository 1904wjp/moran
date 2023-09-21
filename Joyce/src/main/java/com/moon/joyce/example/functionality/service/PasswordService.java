package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Password;
import org.springframework.stereotype.Service;

/**
 * web实体类
 */
@Service
public interface PasswordService extends IService<Password> {

    PageVo getList(Password password);

    int getCount(Password password);

    Password getOne(Password password);
}
