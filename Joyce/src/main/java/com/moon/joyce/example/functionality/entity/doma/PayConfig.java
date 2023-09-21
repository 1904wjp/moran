package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/16-- 9:49
 * @describe: 支付配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pay_config")
@Table(content = "支付配置表")
public class PayConfig extends Page {
  
    @Column(comment = "网关名支付宝网关名")
    private String openApiDomain;
    @Column(comment = "沙箱网关名支付宝网关名")
    private String openApiDomainDev;
    @Column(comment = "微云网关名支付宝网关名")
    private String mcloudApiDomain;
    @Column(comment = "此处请填写你的PID")
    private String pid;
    @Column(comment = "沙箱pid")
    private String pidDev;
    @Column(comment = "此处请填写你当面付的APPID")
    private String appid;
    @Column(comment = "沙箱支付")
    private String appidDev;
    @Column(comment = "RSA私钥",type = Type.TEXT)
    private String privateKey;
    @Column(comment = "此处请填写你的应用公钥",type = Type.TEXT)
    private String publicKey;
    @Column(comment = "SHA256withRsa对应支付宝公钥",type = Type.TEXT)
    private String alipayPublicKey;
    @Column(comment = "沙箱支付宝公匙",type = Type.TEXT)
    private String alipayPublicKeyDev;
    @Column(comment = "签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa")
    private String signType;
    @Column(comment = "当面付最大查询次数")
    private String maxQueryRetry;
    @Column(comment = "当面付最大查询查询间隔（毫秒）")
    private String queryDuration;
    @Column(comment = "当面付最大撤销次数")
    private String maxCancelRetry;
    @Column(comment = "当面付最大撤撤销间隔（毫秒）")
    private String cancelDuration;
    @Column(comment = "交易保障线程第一次调度延迟和调度间隔（秒）")
    private String heartbeatDelay;
    @Column(comment = "交易保障线程第一次调度间隔（秒）")
    private String heartbeatDuration;
    @Column(comment = "异步通知url(注意拦截器是否拦截)")
    private String notifyUrl;
    @Column(comment = "使用状态(0:使用，1：未使用)")
    private String status;
    @Column(comment = "私匙修改查看需要钥匙")
    private String privateKeyPassword;
    public static String usedStatus = "0";
    public static String unusedStatus = "1";
    public final static String CLASS_NAME = "PAYCONFIG";
}
