package com.moon.joyce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/13
 * 异步配置
 */
@Component
@EnableAsync
public class JoyceAsyncConfig {
    /*此处成员变量应该使用@Value从配置中读取
     */
    @Value("${async.corePoolSize}")
    private int corePoolSize;
    @Value("${async.maxPoolSize}")
    private int maxPoolSize;
    @Value("${async.queueCapacity}")
    private int queueCapacity;
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

}
