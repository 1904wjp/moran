package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/11
 * 派生列
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("derived_columns")
public class DerivedColumns extends BaseEntity {
    /**
     * 列名
     */
    @TableField("column_name")
    private String columnName;

    /**
     * 列类型
     */
    @TableField("column_type")
    private String columnType;

    /**
     * 列类型
     */
    @TableField("column_desc")
    private String columnDesc;
}
