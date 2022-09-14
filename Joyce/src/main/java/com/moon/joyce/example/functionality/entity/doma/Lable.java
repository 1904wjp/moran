package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/13-- 17:24
 * @describe: 标签
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lable",content = "标签")
@TableName(value = "lable")
public class Lable extends BaseEntity {
    @TableField(value = "name")
    @Column(name = "name",type = Type.VARCHAR,comment = "标签名称")
    private String name;
    @TableField(value = "code")
    @Column(name = "code",type = Type.VARCHAR,comment = "标签代码")
    private String code;
}
