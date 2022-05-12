package com.moon.joyce.interceptor;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/14 15:09
 * @desc 用户登录拦截器
 */
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //重定向到公开主页
        String indexUrl =request.getContextPath()+ Constant.APP_INDEX_PATH;
        User sessionUser = (User) request.getSession().getAttribute(Constant.SESSION_USER);
        if (null!=sessionUser){
            return true;
        }
        response.sendRedirect(indexUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
