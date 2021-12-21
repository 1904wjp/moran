package com.moon.joyce.commons.factory.webFactory.extend.interfaces.impl;

import com.moon.joyce.commons.factory.entity.WebFactoryEntity;
import com.moon.joyce.commons.factory.webFactory.extend.interfaces.WebFile;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.WebEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * web文件实现了类
 */
public class EntityFile implements WebFile {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public int createWebFile(WebEntity webEntity) {
        //创建处理器
        WebFileHandleImpl webFileHandle = new WebFileHandleImpl();
        //获取path
        String path = webFileHandle.getPath("entity", webEntity);
        //检测包和实现继承类名
        WebFactoryEntity factoryEntity = webFileHandle.checkWebFile(webEntity);
        //默认包
        String defaultPackage = "package " + webEntity.getPackageValue() + ".entity;\n\n" +
                "import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                factoryEntity.getPackages() +
                /*"import com.wr.common.core.web.domain.BaseEntity;\n" +*/
                "import java.math.BigDecimal;\n" +
                "import java.util.Date;\n";
         //获取实体类的身体部位
        String entityBody = webFileHandle.getEntityBody(webEntity);
        /*String[] filter = {"create_time","create_by","update_time","update_by"};*/
        //添加代码
        String addCode = "";
        if (StringUtils.isNoneBlank(webEntity.getAddCode())) {
            addCode = webEntity.getAddCode();
        }
        //所有的数据
        defaultPackage = defaultPackage + "\n/**" + webEntity.getColumns()
                .get(0).getTableComment() + "实体类,表" + webEntity.getColumns()
                .get(0).getTableName() + "\n*/\n" + "public class " + StringsUtils
                .getClassName(webEntity.getColumns().get(0).getTableName()) + factoryEntity.getFather()
                + "{\n" + entityBody + "\n" + addCode + "\n}";
           /* if (!webEntity.getType().equals("1")){
                path = otherpack + StringsUtils.getClassName(columns.get(0).getTableName()) + ".java";
            }*/
        //写入文件
        FileUtils.writeFile(path, defaultPackage);
        //检测文件是否存在
        if (FileUtils.fileIsExists(path)) {
            logger.info("创建完成:"+path);
            return 1;
        }
        return 0;
    }
}
