package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/15-- 16:28
 * @describe: 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("good")
@Table(name="good",content = "商品表")
public class Good extends Page {
    @Column(name = "name",comment = "商品名称",type = Type.VARCHAR)
    private String name;

    @Column(name = "price",comment = "商品价格",type = Type.DECIMAL,length = "12")
    private BigDecimal price;

    @TableField("source_id")
    @Column(name = "source_id",comment = "商品图片",type = Type.BIGINT)
    private Long sourceId;

    @NonNull
    @TableField(exist = false)
    private String url;

    @TableField("good_desc")
    @Column(name = "good_desc",comment = "商品描述",type = Type.TEXT)
    private String goodDesc;
}
