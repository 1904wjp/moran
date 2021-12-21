package com.moon.joyce.listeners;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author Xing Dao Rong
 * @date 2021/9/18 16:20
 * @desc 监听http
 */
public class RequestListener {
    /**
     * 监听器：监听HTTP请求事件
     * 解决RequestContextHolder.getRequestAttributes()空指针问题
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
