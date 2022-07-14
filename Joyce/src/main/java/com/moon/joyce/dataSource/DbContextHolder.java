package com.moon.joyce.dataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/25
 */
public class DbContextHolder {
    private final static Logger log = LoggerFactory.getLogger(DbContextHolder.class);
    //对于当前线程的操作-线程安全
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 切换数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){
        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 删除数据源
     */
    public static void deleteDataSource(){
        contextHolder.remove();
        log.info("移除对应数据源，切回主数据源");
    }
}
