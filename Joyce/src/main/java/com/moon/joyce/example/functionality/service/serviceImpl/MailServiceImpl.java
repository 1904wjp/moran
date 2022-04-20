package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.example.functionality.entity.EmailData;
import com.moon.joyce.example.functionality.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author Xing Dao Rong
 * @date 2021/9/3 10:29
 * @desc 邮件实现类
 */
@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;


    @Override
    public void sendHtmlMail(EmailData emailData) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true,"utf-8");
            helper.setFrom(from);
            helper.setTo(emailData.getTo());
            helper.setSubject(emailData.getSubject());
            helper.setText(emailData.getContext(),true);
            mailSender.send(message);
            logger.info("email已发送");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.info("email发送异常");
        }
    }
}
