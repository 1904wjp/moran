package com.moon.joyce.example.functionality.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moon.joyce.example.entity.base.entity.BaseEntity;

/**
 * @author Xing Dao Rong
 * @date 2021/9/16 8:48
 * @desc 发送验证码
 */
public class VerifyCode extends BaseEntity {
    /**
     * 验证码
     */
    @TableField(exist = false)
    private String verifyCodeValue;
    /**
     * 验证有效时间
     */
    @TableField(exist = false)
    private Long vaildTime;

    public VerifyCode() {
    }

    public VerifyCode(String verifyCodeValue, Long vaildTime) {
        this.verifyCodeValue = verifyCodeValue;
        this.vaildTime = vaildTime;
    }

    public String getVerifyCodeValue() {
        return verifyCodeValue;
    }

    public void setVerifyCodeValue(String verifyCodeValue) {
        this.verifyCodeValue = verifyCodeValue;
    }

    public Long getVaildTime() {
        return vaildTime;
    }

    public void setVaildTime(Long vaildTime) {
        this.vaildTime = vaildTime;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "verifyCodeValue='" + verifyCodeValue + '\'' +
                ", vaildTime='" + vaildTime + '\'' +
                '}';
    }
}
