package com.moon.joyce.example.functionality.service;

import com.moon.joyce.example.functionality.entity.EmailData;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 10:23
 * @desc 邮件激活接口
 */
public interface MailService {
    /**
     * 邮件发送
     * @param emailData
     */
    void sendHtmlMail(EmailData emailData);
}
