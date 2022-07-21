package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Column;
import com.moon.joyce.commons.annotation.NotExist;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/19-- 17:02
 * @describe: 相册
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("album")
@Table(name = "album",content = "相册表")
public class Album extends Page {
    @TableField("config")
    @Column(name = "config",length = "0",type = Type.TEXT,comment = "相片集合配置")
    private String sourceConfig;

    @TableField("name")
    @Column(name = "name",type = Type.VARCHAR,comment = "相册名称")
    private String name;

    @TableField("user_id")
    @Column(name = "user_id",type = Type.BIGINT,comment = "用户id")
    private Long userId;

    @Column(name = "type",type = Type.VARCHAR,comment = "类型")
    private String type;

    @Column(name = "total",type = Type.BIGINT,comment = "张数")
    private Integer total;

    @NotExist
    @TableField(exist = false)
    private Map<String,Source> map;
}
