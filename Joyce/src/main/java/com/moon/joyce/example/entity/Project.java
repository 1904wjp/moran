package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
     * 项目id
     */
    @TableField("project_id")
    private Long projectId;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 项目介绍
     */
    @TableField("desc")
    private String desc;
}
