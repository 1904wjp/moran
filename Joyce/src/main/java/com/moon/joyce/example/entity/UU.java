package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class UU extends BaseEntity {
    private static final long serialVersionUID = -6234658347872749729L;
    /**
     * 用户id
     */
    @TableField("usera_id")
    private Long userAId;
    /**
     * 另一个用户id
     */
    @TableField("userb_id")
    private Long userBId;
    /**
     * 用户关系类型
     */
    private String type;
}
