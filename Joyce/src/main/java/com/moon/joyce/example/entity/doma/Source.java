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
    @Column(exist = false)
    @TableField(exist = false)
    private String username;
    /**
     * 应用状态【0:普通1:主页;2:主页选项;3:备用;4:相册】
     */
    @TableField("apply_status")
    @Column(comment = "应用状态【0:普通1:主页;2:主页选项;3:备用;4:相册】")
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
    @Column(exist = false)
    @TableField(exist = false)
    private String vUrl;

    @TableField("real_url")
    private String realUrl;

    @Column(exist = false)
    @TableField(exist = false)
    private Integer powerLevel;

    @Column(comment = "是否公开[0:不公开;1:公开]")
    private Integer isPub;

    @Column(exist = false,comment = "查询类型[0:资源页面;1:main页面]")
    @TableField(exist = false)
    private Integer sType;

    private String lable;
}
