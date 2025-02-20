package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Ids;
import com.moon.joyce.commons.annotation.auto.NotExist;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 类包
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("package_info")
@Table(name = "package_info",content = "包配置")
public class PackageInfo extends Page {

    private static final long serialVersionUID = 1814178285136632126L;
    /**
     * 包名
     */
    @Ids
    @TableField("package_name")
    private String packageName;
    /**
     * 包值
     */
    @TableField("package_value")
    private String packageValue;
    /**
     * 数据库用户
     */
    @Column(exist = false)
    @TableField(exist = false)
    private String username;
    /**
     * 应用状态
     */
    @TableField("apply_status")
    private Integer applyStatus;
    /**
     * 当前用户
     */
    @TableField("user_id")
    private Long userId;
    /**
     * web实体类id
     */
    @TableField("web_entity_id")
    private Long webEntityId;


    /**
     * 项目对应的地址
     */
    @TableField("project_address")
    private String projectAddress;
}
