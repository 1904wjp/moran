package com.moon.joyce.example.functionality.entity.doma;



import java.io.Serializable;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 9:06
 * @desc 返回Result类型
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 8667410620705660507L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 结果状态
     */
    private Boolean rs;
    /**
     * 传输的数据
     */
    private Object data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getRs() {
        return rs;
    }

    public void setRs(Boolean rs) {
        this.rs = rs;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(Integer code, String msg, Boolean rs, Object data) {
        this.code = code;
        this.msg = msg;
        this.rs = rs;
        this.data = data;
    }

    public Result(Integer code, String msg, Boolean rs) {
        this.code = code;
        this.msg = msg;
        this.rs = rs;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
