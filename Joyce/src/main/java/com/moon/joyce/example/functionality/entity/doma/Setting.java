package com.moon.joyce.example.functionality.entity.doma;

import com.moon.joyce.example.entity.doma.DbBaseSetting;
import com.moon.joyce.example.entity.doma.PackageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/13
 * @describe: 数据库配置
 */
public class Setting implements Serializable {
    private static final long serialVersionUID = -4731753461634986L;
    /**
     * 数据源对象配置
     */
    private DbBaseSetting dbBaseSetting;
    /**
     * 数据包对象配置
     */
    private PackageInfo packageInfo;

    /**
     * 其余的配置
     */
    private Map map;
    public Setting() {
    }
    public Setting(DbBaseSetting dbBaseSetting, PackageInfo packageInfo) {
        this.dbBaseSetting = dbBaseSetting;
        this.packageInfo = packageInfo;
    }
    public Setting(DbBaseSetting dbBaseSetting, PackageInfo packageInfo,Map<String,Object> map) {
        this.dbBaseSetting = dbBaseSetting;
        this.packageInfo = packageInfo;
        this.map = map;
    }

    public DbBaseSetting getDbBaseSetting() {
        return dbBaseSetting;
    }

    public void setDbBaseSetting(DbBaseSetting dbBaseSetting) {
        this.dbBaseSetting = dbBaseSetting;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
