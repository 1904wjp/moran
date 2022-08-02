package com.moon.joyce.example.functionality.enums;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/01-- 16:41
 * @describe:相册类型
 */
public enum AlbumEnum {
    BOX(0,12);
    private Integer code;
    private Integer length;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    AlbumEnum(Integer code, Integer length) {
        this.code = code;
        this.length = length;
    }
}
