package com.moon.joyce.example.functionality.entity.doma;

import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 16:18
 * @describe:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uri extends BaseEntity {
    private String name;
    private String remark;
    private String url;
    private String params;
}
