package com.moon.joyce.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/10-- 10:09
 * @describe:
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
