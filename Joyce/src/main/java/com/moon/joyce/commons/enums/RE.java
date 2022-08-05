package com.moon.joyce.commons.enums;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/03-- 16:39
 * @describe:
 */
public enum RE {
    ADD(0),
    UPDATE(1),
    DELETE(2),
    SELECT(3),
    ADDORUPDATE(4);
    private Integer code;

    public Integer getCode() {
        return code;
    }

    RE(Integer code) {
        this.code = code;
    }
}
