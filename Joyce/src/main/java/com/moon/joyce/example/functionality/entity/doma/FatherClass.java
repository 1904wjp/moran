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
 * 继承实现的父类信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("father_class")
public class FatherClass extends BaseEntity {
    private static final long serialVersionUID = -1831757619357230981L;
    /**
     * 实现继承父类的名称
     */
    @TableField("father_class_name")
    private String fatherClassName;
    /**
     * 实现继承父类的描述
     */
    @TableField("father_class_desc")
    private String fatherClassDesc;
    /**
     * 对应包id
     */
    @TableField("package_id")
    private Long packageId;
    /**
     * 对应包名称
     */
    @TableField(exist = false)
    private String packageName;
}
