package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author Xing Dao Rong
 * @date 2021/9/2 17:51
 * @desc  邮件工具类
 */
public class EmailUtils implements Serializable {
    private static final long serialVersionUID = 4647492534040558478L;
    private EmailUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    /**
     * 发送验证码
     * @param recipient 邮件地址
     * @param num 随机验证码的位数
     */
    public  static String SendMailCode(String recipient,int num) {
        //做链接前的准备工作  也就是参数初始化
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port", "465");//发送端口
        properties.setProperty("mail.smtp.auth", "true");//是否开启权限控制
        properties.setProperty("mail.debug", "true");//true 打印信息到控制台
        properties.setProperty("mail.transport", "smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable", "true");
        //建立两点之间的链接
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.SEND_EMAIL_CODE, Constant.SEND_EMAIL_AUTH_CODE);
                //这里第一个参数是发件人邮箱号码，第二个是邮箱的验证码下面解释
            }
        });
        //创建验证码
        String code="";
        //创建邮件对象
        Message message = new MimeMessage(session);
        try {
            //设置发件人
            message.setFrom(new InternetAddress(Constant.SEND_EMAIL_CODE));
            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));//收件人
            //随机生成验证码
            code = RandomStringUtils.random( num,"0123456789".toCharArray() );
            //设置主题
            message.setSubject(code);
            //设置邮件正文  第二个参数是邮件发送的类型
            String context = Constant.SEND_EMAIL_TEMPLATE+code;
            message.setContent(context, Constant.SEND_EMAIL_FORMATE);
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect(Constant.SEND_EMAIL_CODE, Constant.SEND_EMAIL_PASSWORD);
            //这里两个参数是发件人 qq账号和密码
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }

}
