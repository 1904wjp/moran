package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Ids;
import com.moon.joyce.commons.annotation.auto.NotExist;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/4
 * 聊天记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_record")
@Table(name = "chat_record",content = "记录表")
public class ChatRecord extends BaseEntity {
    private static final long serialVersionUID = 7558750959590695111L;
    /**
     * 用户a id
     */
    @Ids
    @TableField("user_a_id")
    private Long userAId;
    /**
     * 用户a名称
     */
    @NotExist
    @TableField(exist = false)
    private String userAName;
    /**
     * 用户b id
     */
    @TableField("user_b_id")
    private Long userBId;
    /**
     * 用户b名称
     */
    @NotExist
    @TableField(exist = false)
    private String userBName;
    /**
     * 用户聊天信息
     */
    @TableField("content")
    private String content;
    /**
     * 用户a 图像
     */
    @NotExist
    @TableField(exist = false)
    private String aFileUrl;
    /**
     * 用户b 图像
     */
    @NotExist
    @TableField(exist = false)
    private String bFileUrl;
    /**
     * 用户a 昵称
     */
    @NotExist
    @TableField(exist = false)
    private String aNickname;
    /**
     * 用户b 昵称
     */
    @NotExist
    @TableField(exist = false)
    private String bNickname;
    /**
     * 聊天记录生成时间
     */
    @NotExist
    @TableField(exist = false)
    private String createTimeValue;

    /**
     * redis聊天记录公用标识
     */
    @NotExist
    @TableField(exist = false)
    public static String uniqueAppend = "chatRecord:";

    public ChatRecord(Long userAId, Long userBId) {
        this.userAId = userAId;
        this.userBId = userBId;
    }




}
