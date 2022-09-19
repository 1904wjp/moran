package com.moon.joyce.example.functionality.entity.doma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/18-- 16:31
 * @describe: 密码输入规定请求次数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCount {
    //最大错误次数
    private int count;
    //开始限制的时间
    private Date limitTime;


}
