package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_base_setting")
@Table(name = "db_base_setting",content = "数据库配置")
public class DbBaseSetting extends Page {
    private static final long serialVersionUID = 5736882997195936413L;
    /**
     * 数据源名称
     */
    @Ids
    @TableField("data_source_name")
    private String dataSourceName;
    /**
     * 数据库url
     */
    private String url;
    /**
     * 数据库用户
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;

    /**
     * 驱动
     */
    private String driverName;

    /**
     * 项目地址
     */
    @TableField("project_address")
    private String projectAddress;

    /**
     * 暂留字段
     */
    @TableField("temp_code")
    private String tempCode;

    /**
     * 数据源类型
     */
    @TableField("database_type")
    private String databaseType;

    /**
     * 登录人的id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 应用状态
     */
    @TableField("apply_status")
    private Integer applyStatus;


}
