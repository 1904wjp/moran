package com.moon.joyce.commons.factory.entity;

import com.moon.joyce.commons.factory.enums.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private Boolean unique;

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

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
     * 元素添加
     * @param columnEntities
     * @param columnEntity
     * @return
     */
    private static Integer currentIndex = 0;
    private static Integer size = 0;
    public static ColumnEntity[] ArrayAdd(ColumnEntity[] columnEntities, ColumnEntity columnEntity){
        ColumnEntity[] newColumnEntitys =  new ColumnEntity[columnEntities.length+1];
        if (newColumnEntitys.length>currentIndex){
            System.arraycopy(columnEntities,0,newColumnEntitys,0,columnEntities.length);
            newColumnEntitys[columnEntities.length] = columnEntity;
            currentIndex = columnEntities.length;
            size = newColumnEntitys.length;
        }
        return newColumnEntitys;
    }

    public static Integer size(){
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColumnEntity that = (ColumnEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, comment, isKey, auto, type, isNotNull, idValue, defaultValue);
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
}