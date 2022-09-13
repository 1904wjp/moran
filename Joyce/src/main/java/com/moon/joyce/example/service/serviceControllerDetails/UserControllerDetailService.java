package com.moon.joyce.example.service.serviceControllerDetails;

import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Setting;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:10
 * @describe:  Controller细节层：控制层比较繁琐的方法将在这里完成
 */
public interface UserControllerDetailService {
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

    /**
     * 注册基础数据检测
     * @param user
     * @return
     */
    Result checkRegistData(User user);

    /**
     * 用户状态数据检测
     * @param user
     * @return
     */
    Result checkStatusData(User user);
}
