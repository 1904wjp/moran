package com.moon.joyce.commons.factory.entity.auto;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 20:26
 * @describe:
 */

import com.moon.joyce.commons.annotation.joyce.CreateEntity;
import com.moon.joyce.example.functionality.entity.doma.Column;
import lombok.Data;

/**
 * 表实体类
 */
@CreateEntity
@Data
public class TableEntity{
    /**
     * 表名
     */
    private String name;
    /**
     * 表注释
     */
    private String content;
    /**
     * 表策略
     */
    private String strategy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public TableEntity() {
    }

    /**
     * 转换
     * @param column
     * @return
     */
    public TableEntity columnToTableEntity(Column column){
        this.name = column.getTableName();
        this.content = column.getTableComment();
        return this;
    }
    @Override
    public String toString() {
        return "TableEntity{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", tableStrategy=" + strategy +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

