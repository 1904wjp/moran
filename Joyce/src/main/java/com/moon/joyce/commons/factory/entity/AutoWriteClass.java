package com.moon.joyce.commons.factory.entity;

import com.moon.joyce.example.functionality.entity.doma.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * webFactory
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoWriteClass implements Serializable {
    private static final long serialVersionUID = 2306128673903397125L;
    /**
     * 前端传入的字段
     */
    private Map<String,Type> values;
    /**
     * 主要的类容
     */
    private String content;
    /**
     * 表
     */
    private List<Column> columns;
}
