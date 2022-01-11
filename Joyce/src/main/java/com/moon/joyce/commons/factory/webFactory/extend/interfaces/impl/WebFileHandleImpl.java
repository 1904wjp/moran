package com.moon.joyce.commons.factory.webFactory.extend.interfaces.impl;

import com.moon.joyce.commons.factory.entity.WebFactoryEntity;
import com.moon.joyce.commons.factory.webFactory.extend.interfaces.WebFileHandle;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.functionality.entity.WebEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * webFile处理器
 */
public class WebFileHandleImpl implements WebFileHandle {
    @Override
    public WebFactoryEntity checkWebFile(WebEntity webEntity) {
        String father = "";
        String sumPackage = "";
        if (1==webEntity.getIsExtends()){
            father = "extends "+webEntity.getExtendFather();
            sumPackage = "import "+webEntity.getExtendPackageValue()+"\n";
        }
        //是否实现
        if (1==webEntity.getIsImplements()){
            //实现的是多个继承
            String[] implementFathers = webEntity.getImplementFatherIds().split(",");
            for (String implementFather : implementFathers) {
                father = father +" "+implementFather;
            }
            for (String implPackage : webEntity.getImplementPackageValue()) {
                sumPackage = sumPackage +"\n\t\timport "+implPackage;
            }
        }
        //需要的包是否为为空
        if (StringUtils.isNoneBlank(webEntity.getAddPackageIds())){
            for (String addPackageValue : webEntity.getAddPackageValues()) {
                sumPackage=sumPackage+"\n\t\timport "+addPackageValue;
            }
        }
        return new WebFactoryEntity(father,sumPackage);
    }

    @Override
    public String getPath(String strName,WebEntity webEntity) {
        String path = System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\files\\";
        if (StringUtils.isNoneBlank(webEntity.getProjectAddress())) {
            path = webEntity.getProjectAddress() + "/"+strName+"/" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + ".java";
        }
        return path;
    }

    @Override
    public String getEntityBody(WebEntity webEntity) {
        String str = "";
        List<Column> list = webEntity.getColumns();
        if (!webEntity.getFilterColumnNames().isEmpty()) {
            for (int i1 = 0; i1 < webEntity.getFilterColumnNames().size(); i1++) {
                for (int i = 0; i < webEntity.getColumns().size(); i++) {
                    if (webEntity.getColumns().get(i).getColumnName().equals(webEntity.getFilterColumnNames().get(i1))) {
                        list.remove(webEntity.getColumns().get(i));
                    }

                }
            }
        }
        for (Column column : list) {
            String temp = "/**\n*" + column.getColumnComment() + "\n*/\n" +
                    "private " + StringsUtils.changeType2(StringsUtils.changeType(column.getColumnType())) +
                    " " + StringsUtils.getUp(column.getColumnName()) + ";\n";
            str = str + temp;

        }
        return str;
    }


}
