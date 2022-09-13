package com.moon.joyce.commons.factory.entity;

import com.moon.joyce.example.functionality.entity.doma.Column;

import java.util.Objects;

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
     * 序号
     */
    private Integer idValue;
    /**
     * 唯一
     */
    private Boolean unique;
    /**
     * 默认值
     */
    private String defaultValue;

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }


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

    public Integer getIdValue() {
        return idValue;
    }

    public void setIdValue(Integer idValue) {
        this.idValue = idValue;
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

    /**
     * 转换
     * @param column
     */
    public void columnToColumnEntity(Column column){
        this.name = column.getColumnName();
        this.length = column.getColumnLength();
        this.comment = column.getColumnComment();
        this.defaultValue = column.getDefaultValue();
        this.isKey = "PRI".equals(column.getIsKey());
        this.defaultValue = column.getDefaultValue();
        this.isNotNull = "OK".equals(column.getIsNull());
        this.type = column.getColumnType();
        this.auto = false;
        if (isKey){
            this.auto = true;
        }
        this.unique = false;
        if (this.isKey){
            this.unique = true;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ColumnEntity{");
        sb.append("name='").append(name).append('\'');
        sb.append(", length=").append(length);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", isKey=").append(isKey);
        sb.append(", auto=").append(auto);
        sb.append(", type='").append(type).append('\'');
        sb.append(", isNotNull=").append(isNotNull);
        sb.append(", idValue=").append(idValue);
        sb.append(", defaultValue='").append(defaultValue).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnEntity that = (ColumnEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(length, that.length) && Objects.equals(comment, that.comment) && Objects.equals(isKey, that.isKey) && Objects.equals(auto, that.auto) && Objects.equals(type, that.type) && Objects.equals(isNotNull, that.isNotNull) && Objects.equals(idValue, that.idValue) && Objects.equals(unique, that.unique) && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, comment, isKey, auto, type, isNotNull, idValue, unique, defaultValue);
    }
}