package com.moon.joyce.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/3
 */
@Configuration
public class JoyceConfig {
    /**
     * mapper层包扫描配置
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.moon.joyce.example.mapper");
        return mapperScannerConfigurer;
    }

}
