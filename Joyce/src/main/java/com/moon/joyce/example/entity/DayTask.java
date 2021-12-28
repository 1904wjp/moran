package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/27
 * @desc: 每日看板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("day_task")
public class DayTask extends Page {
    private static final long serialVersionUID = 7069213413492251570L;
    /**
     * 结束时间
     */
    @TableField("end_times")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTimes;
    /**
     * 今日任务
     */
    @TableField("today_task")
    private String todayTask;
    /**
     * 最终任务
     */
    @TableField("finaly_task")
    private String finalyTask;
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
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;
    /**
     * 开始时间
     */
    @TableField(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime2;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime2;

}
