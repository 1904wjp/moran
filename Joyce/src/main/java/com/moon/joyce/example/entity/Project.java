package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: XingDaoRong
 * @Date: 2021/12/27
 * @desc: 每日看板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("project")
public class Project extends Page {
    private static final long serialVersionUID = 7516486402681355169L;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 项目介绍
     */
    @TableField("descs")
    private String descs;
    /**
     * 项目负责人
     */
    @TableField("charge_person")
    private String chargePerson;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
}
