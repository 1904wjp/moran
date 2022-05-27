package com.moon.joyce.commons.factory.entity;

import com.moon.joyce.commons.factory.enums.Type;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 20:27
 * @describe:
 */

public class ColumnEntity{
    /**
     * 列名
     */
    private String name;
    /**
     * 列长度
     */
    private Long length;
    /**
     * 列注释
     */
    private String comment;
    /**
     * 是否为主键
     */
    private Boolean isKey;
    /**
     * 是否自动增长
     */
    private Boolean auto;
    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否不为空
     */
    private Boolean isNotNull;

    /**
     * 默认值
     */
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getType() {
        return type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getKey() {
        return isKey;
    }

    public void setKey(Boolean key) {
        isKey = key;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }




    public ColumnEntity() {
    }

    public Boolean getNotNull() {
        return isNotNull;
    }

    public void setNotNull(Boolean notNull) {
        isNotNull = notNull;
    }

    @Override
    public String toString() {
        return "ColumnEntity{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", content='" + comment + '\'' +
                ", isKey=" + isKey +
                ", auto=" + auto +
                ", type='" + type + '\'' +
                ", isNotNull=" + isNotNull +
                '}';
    }
}