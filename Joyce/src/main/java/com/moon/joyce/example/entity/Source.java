package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.NotExist;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("source")
@Table(name = "source",content = "资源表")
public class Source  extends Page {
    /**
     * 资源名称
     */
    @Ids
    @TableField("source_name")
    private String sourceName;
    /**
     * 类型[0.图片；1.音频；2.视频 3.视频封面]
     */
    private String type;
    /**
     * 资源连接
     */
    private String url;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户姓名
     */
    @NotExist
    @TableField(exist = false)
    private String username;
    /**
     * 应用状态【0:普通页面 1:主页 2:主页选项 3:相册】
     */
    @TableField("apply_status")
    private Integer applyStatus;
    /**
     * 标题注释
     */
    @TableField("desc_content")
    private String descContent;
    /**
     * 绑定视频id
     */
    @TableField("v_id")
    private Long vId;
    /**
     * 视频url
     */
    @NotExist
    @TableField(exist = false)
    private String vUrl;

}
