package com.moon.joyce.example.functionality.entity.connection;

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
 * @date: 2022/09/13-- 17:31
 * @describe: 字典和标签表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dict_and_lable",content = "标签")
@TableName(value = "lable")
public class DictAndLable extends BaseEntity {

    @TableField(value = "dict_id")
    @Column(name = "dict_id",type = Type.VARCHAR,comment = "字典id")
    private Long dictId;

    @TableField(value = "lable_id")
    @Column(name = "lable_id",type = Type.VARCHAR,comment = "标签id")
    private Long lableId;

}
