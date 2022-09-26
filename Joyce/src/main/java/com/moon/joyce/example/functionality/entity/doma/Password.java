package com.moon.joyce.example.functionality.entity.doma;

import com.moon.joyce.commons.annotation.auto.Ids;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/26-- 15:48
 * @describe: 自己的一些密码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(content = "密码表")
public class Password extends BaseEntity {
    @Ids
    private String username;
    private String password;
    private String remark;
}
