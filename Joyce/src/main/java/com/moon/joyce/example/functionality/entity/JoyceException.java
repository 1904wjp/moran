package com.moon.joyce.example.functionality.entity;

import io.swagger.models.auth.In;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/11
 * Joyce异常
 */
public class JoyceException extends Exception{
    /**
     * 异常代码
     */
    private Integer exceptionCode;
    /**
     * 异常消息
     */
    private String exceptionMsg;

    /**
     * 异常类型
     */
    private String exceptionType;

    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public JoyceException() {
    }
    public JoyceException(String exceptionMsge) {
        this.exceptionMsg = exceptionMsg;
    }

    public JoyceException(String exceptionMsg, String exceptionType) {
        this.exceptionMsg = exceptionMsg;
        this.exceptionType = exceptionType;
    }

    public JoyceException(Integer exceptionCode, String exceptionMsg, String exceptionType) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
        this.exceptionType = exceptionType;
    }
}
