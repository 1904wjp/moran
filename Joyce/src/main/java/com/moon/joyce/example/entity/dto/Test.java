package com.moon.joyce.example.entity.dto;

import com.moon.joyce.commons.annotation.Column;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.base.entity.BaseEntity;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 0:57
 * @describe:
 */
@Table(name = "test",content = "测试",strategy = TableStrategy.FORCE)
public class Test extends BaseEntity {
    @Column(name = "names",comment = "测试名称",type = Type.VARCHAR,length = "64")
    private String name;
}
