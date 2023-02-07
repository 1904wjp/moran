package com.moon.joyce.commons.factory.demo;

import com.moon.joyce.commons.annotation.joyce.CreateEntity;
import com.moon.joyce.commons.annotation.joyce.CreateFeild;
import com.moon.joyce.commons.factory.demo.base.BaseFactory;
import com.moon.joyce.commons.factory.inter.TableFactory;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.doma.Column;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 21:50
 * @describe: 自动创建
 */
public class AutoCreate extends BaseFactory implements TableFactory {
    private static Map<String,Class<?>> classMap = new HashMap<>();
    String path = "com";
    /**
     * 根据路径获取加载
     */
    private ClassLoader getClassLoader() {
        return AutoCreate.class.getClassLoader();
    }

    private void  fillClassMap(String path){
        List<File> pack = getClassFileByPack(path,getClassLoader());
        for (File file : pack) {
            ClassLoader classLoader = getClassLoader();
            Class<?> aClass = null;
            try {
                aClass = loadClassByPath(file.getAbsolutePath(), classLoader, null, null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (aClass.isAnnotationPresent(CreateEntity.class)){
                CreateEntity createEntity = aClass.getAnnotation(CreateEntity.class);
                String  key =  createEntity.name();
                if (!classMap.isEmpty()){
                    if (classMap.containsKey(key)){
                        throw new JoyceException(key+"已经存在");
                    }else {
                        if (StringsUtils.isBlank(key)){
                            key = aClass.getSimpleName();
                        }
                        classMap.put(key,aClass);
                    }
                }
            }
        }
    }

    private void createClass(String path){
        List<File> pack = getClassFileByPack(path,getClassLoader());
        for (File file : pack) {
            ClassLoader classLoader = getClassLoader();
            Class<?> aClass = null;
            try {
                aClass = loadClassByPath(file.getAbsolutePath(), classLoader, null, null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (aClass.isAnnotationPresent(CreateFeild.class)){
                CreateFeild createFeild = aClass.getAnnotation(CreateFeild.class);
                if (createFeild.ifOld()){
                    aClass = classMap.get(aClass.getSimpleName());
                }
            }
        }
    }

    @Override
    public String getTableFactoryName() {
        return "自定义装配类";
    }

    @Override
    public List<String> getSqls(Map<String, List<Column>> map) {
        return null;
    }

    @Override
    public void initData(String ps) {
        if (ps==null){
            ps = path;
        }
        fillClassMap(ps);
        createClass(ps);
    }
}
