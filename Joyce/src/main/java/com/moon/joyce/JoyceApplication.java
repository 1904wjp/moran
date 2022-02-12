package com.moon.joyce;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

import java.sql.SQLException;

@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MybatisPlusAutoConfiguration.class,SecurityAutoConfiguration.class})
public class JoyceApplication {

    /**
     * 上下文请求监听
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
    /**
     * log4j日志
     * @return
     */
    public Logger getLog4jLogger(){
        return LogManager.getLogger(this.getClass());
    }
    /**
     * springboot启动类
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(JoyceApplication.class, args);
        System.out.println("___________________________Joyce start successful___________________________");
    }
}
