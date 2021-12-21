package com.moon.joyce.example.functionality.entity;

import com.moon.joyce.example.entity.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/26-- 22:56
 * @describe: 邮件属性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailData extends BaseEntity {
    /**
     * 收件人邮件
     */
    private String to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String context;
}
