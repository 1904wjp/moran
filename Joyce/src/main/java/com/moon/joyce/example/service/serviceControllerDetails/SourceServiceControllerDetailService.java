package com.moon.joyce.example.service.serviceControllerDetails;

import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.Setting;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:10
 * @describe:  Controller细节层：控制层比较繁琐的方法将在这里完成
 */
public interface SourceServiceControllerDetailService {
    /**
     * 去除资源应用状态
     * @param type
     * @param userId
     * @return
     */
    boolean retireApplyStatus(String type,Long userId);

    /**
     * 检查资源
     * @param source
     * @return
     */
    boolean check(Source source);
}
