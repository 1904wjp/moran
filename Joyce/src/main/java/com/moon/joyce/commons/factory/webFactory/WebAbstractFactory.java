package com.moon.joyce.commons.factory.webFactory;

import com.moon.joyce.example.functionality.entity.WebEntity;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * 抽象web工厂
 */
public abstract class WebAbstractFactory {
    //后台创建对应文件
    public abstract String createWebFile(WebEntity webEntity);
}
