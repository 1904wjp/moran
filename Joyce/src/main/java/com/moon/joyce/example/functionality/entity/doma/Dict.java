package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.NotExist;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.*;

import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/13-- 17:15
 * @describe: 字典
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dict",content = "字典表")
@TableName(value = "dict")
public class Dict extends BaseEntity {
    @Column(name = "code",type = Type.VARCHAR,comment = "字典代码")
    private String code;
    @Column(name = "name",type = Type.VARCHAR,comment = "名称")
    private String name;
    @Column(name = "value",type = Type.TEXT,comment = "内容")
    private String value;
    //标签
    @NotExist
    @TableField(exist = false)
    private List<Lable> lables;
    @NotExist
    private String type = "dict";
}
