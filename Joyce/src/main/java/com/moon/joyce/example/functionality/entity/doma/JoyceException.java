package com.moon.joyce.example.functionality.entity.doma;




/**
 * @Author: XingDaoRong
 * @Date: 2021/12/11
 * Joyce异常
 */
public class JoyceException extends RuntimeException {

    private static final long serialVersionUID = 5460755684234005953L;
    /**
     * 异常消息
     */
    private String exceptionMsg;

    private Throwable throwable;

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }


    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public JoyceException() {
    }
    public JoyceException(String exceptionMsg) {
        super(exceptionMsg);
    }
    public JoyceException(Throwable throwable) {
        super(throwable);
    }
    public JoyceException(String exceptionMsg,Throwable throwable) {
        super(exceptionMsg,throwable);
    }
}
