package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/4
 * 聊天记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_record")
public class ChatRecord extends BaseEntity {
    private static final long serialVersionUID = 7558750959590695111L;
    /**
     * 用户a id
     */
    @TableField("user_a_id")
    private Long userAId;
    /**
     * 用户a名称
     */
    @TableField(exist = false)
    private Long userAName;
    /**
     * 用户b id
     */
    @TableField("user_b_id")
    private Long userBId;
    /**
     * 用户b名称
     */
    @TableField(exist = false)
    private Long userBName;
    /**
     * 用户聊天信息
     */
    @TableField("content")
    private String content;
    /**
     * 用户a 图像
     */
    @TableField(exist = false)
    private String aFileUrl;
    /**
     * 用户b 图像
     */
    @TableField(exist = false)
    private String bFileUrl;
    /**
     * 用户a 昵称
     */
    @TableField(exist = false)
    private String aNickname;
    /**
     * 用户b 昵称
     */
    @TableField(exist = false)
    private String bNickname;
    /**
     * 聊天记录生成时间
     */
    @TableField(exist = false)
    private String createTimeValue;
}
