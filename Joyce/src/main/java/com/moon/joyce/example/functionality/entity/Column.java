package com.moon.joyce.example.functionality.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column implements Serializable{
    /**
     * 表字段名称
     */
    private String columnName;
    /**
     * 表字段类型
     */
    private String columnType;
    /**
     * 表字段注释
     */
    private String columnComment;

    /**
     * 表名字
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;
}
