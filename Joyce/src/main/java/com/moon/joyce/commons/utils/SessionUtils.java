package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/27-- 0:52
 * @describe: session工具类
 */
public class SessionUtils {
    //获得session
    public static HttpSession getSession(){
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
    //获取在线用户
    public static User getSessionUser(){
        return  (User) getSession().getAttribute(Constant.SESSION_USER);
    }
}
