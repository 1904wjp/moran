package com.moon.joyce.commons.environment;


import com.moon.joyce.commons.utils.JoyceExceptionUtils;
import com.moon.joyce.example.functionality.entity.JoyceException;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/12
 * 自定义运行环境
 */
@Component
public class JoyceEnvironmentPostProcessor implements EnvironmentPostProcessor {
    //创建Properties对象
    private final Properties properties = new Properties();
    @SneakyThrows
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //自定义配置文件
        String[] profiles = {"joyce.properties"};
        for (String profile : profiles) {
            ClassPathResource resource = new ClassPathResource(profile);
            environment.getPropertySources().addLast(loadProfiles(resource));
        }
    }

    private PropertySource<?> loadProfiles(Resource resource) throws JoyceException {
        if (!resource.exists()){
         throw JoyceExceptionUtils.exception("资源"+resource+"不存在");
        }
        try {
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(Objects.requireNonNull(Objects.requireNonNull(resource.getFilename())),properties);
        } catch (IOException e) {
           throw JoyceExceptionUtils.exception("加载配置文件失败"+resource,e);
        }
    }
}
