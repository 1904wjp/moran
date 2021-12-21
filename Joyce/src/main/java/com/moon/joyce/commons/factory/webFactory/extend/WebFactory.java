package com.moon.joyce.commons.factory.webFactory.extend;

import com.moon.joyce.commons.factory.webFactory.WebAbstractFactory;
import com.moon.joyce.commons.factory.webFactory.extend.interfaces.impl.EntityFile;
import com.moon.joyce.example.functionality.entity.WebEntity;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * 继承抽象工厂
 */
public class WebFactory extends WebAbstractFactory {
    @Override
    public String createWebFile(WebEntity webEntity) {
        if (webEntity.getType().equals("0")){
            EntityFile entityFile = new EntityFile();
            int entityResult = entityFile.createWebFile(webEntity);
        }
        return "";
    }
}
