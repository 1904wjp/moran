package com.moon.joyce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 17:44
 * @desc 资源映射
 */
public class JoyceWebAppConfigurer implements WebMvcConfigurer {

    /**
     * 图片上传地址
     */
    @Value("${file.upload.path}")
    private String filePath;

    /**
     * 显示相对地址
     */
    @Value("${file.upload.relative}")
    private String fileRelativePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler(fileRelativePath).
               addResourceLocations("file:/"+filePath);
    }
}
