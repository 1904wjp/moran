package com.moon.joyce.example.service.serviceControllerDetails;

import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.Setting;
import org.springframework.stereotype.Service;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:10
 * @describe:  Controller细节层：控制层比较繁琐的方法将在这里完成
 */
public interface UserServiceControllerDetailService {
    /**
     * 创建发送获得发送的邮件地址
     * @param user
     * @return
     */
    String getEmailAddress(User user,String appUrl);

    /**
     * 查询是否有主数据源
     * @param userId
     * @return
     */
    Setting checkData(Long userId);

    //注册基础数据检测
    Result checkRegistData(User user);

    //登录基础数据检测
    Result checkLoginData(User user);
}
