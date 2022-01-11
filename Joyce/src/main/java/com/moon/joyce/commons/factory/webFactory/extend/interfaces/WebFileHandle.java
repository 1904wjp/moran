package com.moon.joyce.commons.factory.webFactory.extend.interfaces;

import com.moon.joyce.commons.factory.entity.WebFactoryEntity;
import com.moon.joyce.example.functionality.entity.WebEntity;

public interface WebFileHandle {
     /**
      * 检测并且处理包和继承实现类名
      * @param webEntity
      * @return
      */
     WebFactoryEntity checkWebFile(WebEntity webEntity);

     /**
      * 获取路径
      * @param strName
      * @return
      */
     String getPath(String strName,WebEntity webEntity);

     /**
      * 获取实体类体
      * @param webEntity
      * @return
      */
     String getEntityBody(WebEntity webEntity);

     /**
      * 获取过滤实体类
      * @param webEntity
      * @return
      */
     String getEntityFilter(WebEntity webEntity);
}
