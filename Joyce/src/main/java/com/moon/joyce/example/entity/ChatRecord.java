package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.constants.Constant;
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
    private String userAName;
    /**
     * 用户b id
     */
    @TableField("user_b_id")
    private Long userBId;
    /**
     * 用户b名称
     */
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

    /**
     * redis聊天记录公用标识
     */
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

    /**
     * 将对象获取出来
     * @param jedis
     * @param uniqueAppend
     * @return
     */
    @Deprecated
    public static   ChatRecord jGet(Jedis jedis ,String uniqueAppend){
        Map<String, String> map = jedis.hgetAll(uniqueAppend);
        if (map.isEmpty()){
            return  null;
        }
        ChatRecord chatRecord = new ChatRecord();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("id")){
                chatRecord.setId(Long.valueOf(entry.getValue()));
            }
            if (entry.getKey().equals("createBy")){
                chatRecord.setCreateBy(entry.getValue());
            }
            if (entry.getKey().equals("createTime")){
                chatRecord.setCreateTime(DateUtils.strToDate(entry.getValue(), Constant.DATE_TIME_SECOND));
            }
            if (entry.getKey().equals("updateBy")){
                chatRecord.setUpdateBy(entry.getValue());
            }
            if (entry.getKey().equals("updateTime")){
                chatRecord.setUpdateTime(DateUtils.strToDate(entry.getValue(), Constant.DATE_TIME_SECOND));
            }
            if (entry.getKey().equals("id")){
                chatRecord.setId(Long.valueOf(entry.getValue()));
            }
            if (entry.getKey().equals("userAId")){
                chatRecord.setUserAId(Long.valueOf(entry.getValue()));
            }
            if (entry.getKey().equals("userBId")){
                chatRecord.setUserAId(Long.valueOf(entry.getValue()));
            }
            if (entry.getKey().equals("userAName")){
                chatRecord.setUserAName(entry.getValue());
            }
            if (entry.getKey().equals("userBName")){
                chatRecord.setUserBName(entry.getValue());
            }
            if (entry.getKey().equals("aFileUrl")){
                chatRecord.setAFileUrl(entry.getValue());
            }
            if (entry.getKey().equals("bFileUrl")){
                chatRecord.setBFileUrl(entry.getValue());
            }
            if (entry.getKey().equals("aNickname")){
                chatRecord.setANickname(entry.getValue());
            }
            if (entry.getKey().equals("bNickname")){
                chatRecord.setBNickname(entry.getValue());
            }
            if (entry.getKey().equals("createTimeValue")){
                chatRecord.setCreateTimeValue(entry.getValue());
            }
        }
        return chatRecord;
    }
}
