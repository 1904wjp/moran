package com.moon.joyce.example.functionality.entity;

import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据源实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSource implements Serializable {
    private static final long serialVersionUID = -5828440143913957240L;
    /**
     * 数据源名称
     */
    private String dataSourceName;
    /**
     * 数据源url
     */
    private String url;
    /**
     * 数据源用户名
     */
    private String userName;
    /**
     * 数据源密码
     */
    private String passWord;
    /**
     * 数据源暂存字符
     */
    private String code;
    /**
     * 数据源类型
     */
    private String databaseType;
    /**
     * 驱动
     */
    private String driver;
}
