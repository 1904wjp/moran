package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Author: XingDaoRong
 * @Date: 2021/12/13
 * web实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("web_entity")
public class WebEntity extends BaseEntity   {
    /**
     * web名称
     */
    @TableField("web_name")
    private String webName;

    /**
     * web包
     */
    @TableField("package_id")
    private Long packageId;
    /**
     * 包类
     */
    @TableField(exist = false)
    private String packageValue;
    /**
     * 项目地址
     */
    @TableField(exist = false)
    private String projectAddress;
    /**
     * 是否有继承类(0：没有；1：有)
     */
    @TableField("is_extends")
    private Integer isExtends;
    /**
     * 继承的父类id
     */
    @TableField("extend_father_id")
    private String extendFatherId;
    /**
     * 继承的父类名称
     */
    @TableField("extend_father")
    private String extendFather;
    /**
     * 继承的父类的包
     */
    @TableField("extend_package_id")
    private Long extendPackageId;
    /**
     * 继承的父类的包
     */
    @TableField(exist = false)
    private String extendPackageValue;
    /**
     * 是否实现类(0：没有；1：有)
     */
    @TableField("is_implements")
    private Integer isImplements;
    /**
     * 实现的父类id集合
     */
    @TableField("implement_father_ids")
    private String implementFatherIds;
    /**
     * 实现的父类id的包集合
     */
    @TableField("implement_package_ids")
    private String implementPackageIds;

    @TableField(exist = false)
    private List<String> implementPackageValue;
    /**
     * 添加代码
     */
    @TableField("add_code")
    private String addCode;
    /**
     * 代码顺序
     */
    private Integer order;
    /**
     * 添加代码的包，对应表package_info
     */
    @TableField("add_package_ids")
    private String addPackageIds;

    /**
     * 添加代码的值
     */
    @TableField(exist = false)
    private List<String> addPackageValues;

    /**
     * 表所有列
     */
    @TableField(exist = false)
    List<Column> columns;
    /**
     * 注解Id
     */
    @TableField("annotation_id")
    private Long annotationId;
    /**
     * 注解值
     */
    @TableField(exist = false)
    private String annotationValue;
    /**
     * 创建类型
     */
    String type;
    /**
     * 过滤列ids
     */
    String filterColumnIds;
    /**
     * 过滤集合
     */
    List<String> filterColumnNames;
}
