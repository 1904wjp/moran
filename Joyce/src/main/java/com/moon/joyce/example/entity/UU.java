package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.NotExist;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/20
 * 用于用户之间关联
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("u_u")
@Table(name = "u_u",strategy = TableStrategy.FORCE)
public class UU extends BaseEntity {
    private static final long serialVersionUID = -6234658347872749729L;
    /**
     * 用户id
     */
    @Ids
    @TableField("user_a_id")
    private Long userAId;
    /**
     * 另一个用户id
     */
    @NotExist
    @TableField(exist = false)
    private String usernameA;
    /**
     * 另一个用户id
     */
    @TableField("user_b_id")
    private Long userBId;
    /**
     * 另一个用户名
     */
    @NotExist
    @TableField(exist = false)
    private String usernameB;
    /**
     * 用户关系类型类型{1.好友，2.情侣，3.闺蜜，4.基友，5.黑名单}
     */
    private String type;
    /**
     * 添加好友时的一段话，最多60字
     */
    @TableField("friend_desc")
    private String friendDesc;
    /**
     * 唯一标识
     */
    @NotExist
    @TableField(exist = false)
    public static String uniqueAppend = "uu:";
    /**
     * 加好友结果("1:拒绝，0：同意 2:未处理")
     */
    @NotExist
    @TableField(exist = false)
    private String resultStr ;

    /**
     * 用户图像
     */
    @NotExist
    @TableField(exist = false)
    private String userFileUrlA ;

    /**
     * 用户图像
     */
    @NotExist
    @TableField(exist = false)
    private String userFileUrlB ;

    /**
     * 是否是发送者(0:是 1：否)
     */
    @NotExist
    @TableField(exist = false)
    private String isSendMan ;

}
