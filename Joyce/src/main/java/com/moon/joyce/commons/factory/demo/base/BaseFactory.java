package com.moon.joyce.commons.factory.demo.base;

import com.moon.joyce.example.functionality.entity.JoyceException;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/05-- 15:08
 * @describe:
 */
public class BaseFactory {
    //类型信息
    protected Object[] typeName = {
    "byte","Byte",0,
    "short","Short",0,
    "int","Integer",0,
    "long","Long",0L,
    "float","Float",0.0f,
    "double","Double",0.0,
    "boolean","Boolean",false,
    "char","Character",0};
    //下标基准
    private static final int INDEX = 1;

    /**
     * 获取类型的初始值
     * @param type
     * @return
     */
    protected Object getTypeValue(String type){
        Object o = null;
        for (int i = 0; i < typeName.length; i++) {
            if ((i+ INDEX)%3 == 0){
                if (type.contains(String.valueOf(typeName[i-1])) || type.contains(String.valueOf(typeName[i-2]))){
                    o = typeName[i];
                    break;
                }
            }
        }
        return o;
    }

    /**
     * 获取所有类型
     * @return
     */
    protected Set<String> getAllTypes(){
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < typeName.length; i++) {
            if ((i+ INDEX)%3 == 0){
                continue;
            }
            set.add(typeName[i].toString());
        }
        return set;
    }

    /**
     * 获取类方法
     * @param loadClass
     * @return
     */
    protected Method[] getMethod(Class<?> loadClass) {
        return loadClass.getDeclaredMethods();
    }

    /**
     * 获取方法参数类型
     * @param md
     * @return
     */
    protected Type[] getType(Method md){
        return md.getGenericParameterTypes();
    }

    /**
     * 获取方法参数
     * @param md
     * @return
     */
    protected Parameter[] getParams(Method md){
        return md.getParameters();
    }

    /**
     * 检测里面是否有子文件
     * @param packages
     */
    protected void checkIsParentFile(String[] packages, Class<?> clazz) {
        Set<String> set = Arrays.stream(packages).collect(Collectors.toSet());
        if (set.contains(null) || set.contains("")) {
            throw new NullPointerException("配置包不可含有null或者''");
        }
        if (packages.length < 2) {
            return;
        }
        for (int i = 0; i < packages.length; i++) {
            File f1 = getFile(packages[i],clazz);
            for (int j = i + 1; j < packages.length; j++) {
                File f2 = getFile(packages[j],clazz);
                if (f1.equals(f2.getParentFile()) || f2.equals(f1.getParentFile())) {
                    throw new JoyceException(packages[i] + "与" + packages[j] + "是相互包含的关系，必须选择一个");
                }
            }
        }
    }

    /**
     * 根据路径获取对应文件
     * @param packagePath
     * @return
     */
    protected File getFile(String packagePath, Class<?> clazz) {
        ClassLoader classLoader = getClassLoader(clazz);
        String rePackageName = packagePath.replace(".", "/");
        URL resource = classLoader.getResource(rePackageName);
        return new File(resource != null ? resource.getFile() : null);
    }

    /**
     * 获取类加载
     * @param clazz
     * @return
     */
    protected ClassLoader getClassLoader(Class<?> clazz) {
        return clazz.getClassLoader();
    }

}
