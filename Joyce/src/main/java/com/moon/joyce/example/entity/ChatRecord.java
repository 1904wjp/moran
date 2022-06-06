package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.NotExist;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/4
 * 聊天记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_record")
@Table(name = "chat_record",content = "聊天记录",strategy = TableStrategy.FORCE)
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

    /**
     * 将对象存入redis中
     * @param jedis 当前redis对象
     * @param chatRecord 聊天对象
     * @param uniqueAppend 唯一标识
     */
    @Deprecated
    public static void jPut(Jedis jedis , ChatRecord chatRecord ,String uniqueAppend){
        Map<String,String> redisMap = new HashMap<>();
        redisMap.put("id",chatRecord.getId().toString());
        redisMap.put("createBy",chatRecord.getCreateBy());
        redisMap.put("createTime",chatRecord.getCreateTime().toString());
        redisMap.put("updateBy",chatRecord.getUpdateBy());
        redisMap.put("updateTime",chatRecord.getUpdateTime().toString());
        redisMap.put("deleteFlag",chatRecord.getDeleteFlag().toString());
        redisMap.put("userAId",chatRecord.getUserAId().toString());
        redisMap.put("userBId",chatRecord.getUserBId().toString());
        redisMap.put("userAName",chatRecord.getUserAName());
        redisMap.put("userBName",chatRecord.getUserBName());
        redisMap.put("aFileUrl",chatRecord.getAFileUrl());
        redisMap.put("bFileUrl",chatRecord.getBFileUrl());
        redisMap.put("aNickname",chatRecord.getANickname());
        redisMap.put("bNickname",chatRecord.getBNickname());
        redisMap.put("createTimeValue",chatRecord.getCreateTimeValue());
        jedis.hset(uniqueAppend,redisMap);
    }


}
