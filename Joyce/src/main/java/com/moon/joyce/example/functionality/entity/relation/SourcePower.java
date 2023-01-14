package com.moon.joyce.example.functionality.entity.relation;

import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.Data;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/01/07-- 14:21
 * @describe:
 */
@Data
@TableName("source_power")
@Table(content = "资源下载权限")
public class SourcePower extends BaseEntity {
    @Column(comment = "资源id",type = Type.BIGINT)
    private Long sourceId;
    @Column(comment = "权限等级[0:查看;1:查看和下载]",type = Type.BIGINT,length = "1")
    private Integer powerLevel;
}
