package com.moon.joyce.commons.factory.enums;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/16-- 10:43
 * @describe: 列举数据类型
 */
public enum Type {

    BINARY(1,"BINARY"),
    BIT(2,"BIT"),
    BLOB(3,"BLOB"),
    CHAR(4,"CHAR"),
    DATE(5,"DATE"),
    DATETIME(6,"DATETIME"),
    DECIMAL(7,"DECIMAL"),
    DOUBLE(8,"DOUBLE"),
    ENUM(9,"ENUM"),
    FLOAT(10,"FLOAT"),
    GEOMETRY(11,"GEOMETRY"),
    GEOMETRYCOLLECTION(12,"GEOMETRYCOLLECTION"),
    INT(13,"INT"),
    INTEGER(14,"INTEGER"),
    JSON(15,"JSON"),
    LINESTRING(16,"LINESTRING"),
    LONGBLOB(17,"LONGBLOB"),
    LONGTEXT(18,"LONGTEXT"),
    MEDIUMBLOB(19,"MEDIUMBLOB"),
    MEDIUMINT(20,"MEDIUMINT"),
    MEDIUMTEXT(21,"MEDIUMTEXT"),
    MULTILINESTRING(22,"MULTILINESTRING"),
    MULTIPOINT(23,"MULTIPOINT"),
    MULTIPOLYGON(24,"MULTIPOLYGON"),
    NUMERIC(25,"NUMERIC"),
    POINT(26,"POINT"),
    POLYGON(27,"POLYGON"),
    REAL(28,"REAL"),
    SET(29,"SET"),
    SMALLINT(30,"SMALLINT"),
    TEXT(31,"TEXT"),
    TIME(32,"TIME"),
    TIMESTAMP(33,"TIMESTAMP"),
    TINYBLOB(34,"TINYBLOB"),
    TINYINT(35,"TINYINT"),
    TINYTEXT(36,"TINYTEXT"),
    VARBINARY(37,"VARBINARY"),
    VARCHAR(38,"VARCHAR"),
    YEAR(39,"YEAR"),
    BIGINT(40,"BIGINT");

    private int code;

    private String str;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    Type() {
    }

    Type(int code, String str) {
        this.code = code;
        this.str = str;
    }
}
