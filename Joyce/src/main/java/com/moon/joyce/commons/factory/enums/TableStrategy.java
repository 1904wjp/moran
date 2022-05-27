package com.moon.joyce.commons.factory.enums;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 17:53
 * @describe: 自动创建表的策略
 */
public enum TableStrategy {
    //该策略暂不使用
   // ADD(1,"添加属性"),
    SECURITY(2,"安全创建"),
    FORCE(3,"强制创建");
    private  Integer code;
    private  String value;


    TableStrategy(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
