package com.moon.joyce.commons.factory.demo;

import com.moon.joyce.commons.annotation.url.MethodUrl;
import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.factory.demo.base.BaseFactory;
import com.moon.joyce.commons.factory.entity.url.MethodUrlEntity;
import com.moon.joyce.commons.factory.entity.url.UrlPriEntity;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 15:14
 * @describe:
 */
public class UrlFactory extends BaseFactory {
    private static Map<MethodUrlEntity, UrlPriEntity> map;
    private static UrlFactory urlFactory;

    /**
     * 获取实例
     * @return
     */
    public static UrlFactory getInstance() {
        if (Objects.isNull(urlFactory)) {
            urlFactory = new UrlFactory();
        }
        return urlFactory;
    }

    /**
     * 获取工厂实例
     * @param ps
     * @return
     */
    public static Map<MethodUrlEntity, UrlPriEntity> init(String ps) {
        UrlFactory instance = getInstance();
        if (Objects.isNull(map)) {
            //初始化
            map = new ConcurrentHashMap<>();
            //扫描包
            instance.scannerPackage(ps);
        }
        return map;
    }

    /**
     * 扫描指定文件
     * @param packagePath
     */
    private void scannerPackage(String packagePath) {
        String[] packages;
        if (packagePath.contains(";")) {
            packages = StringUtils.split(packagePath, ";");
        } else {
            packages = new String[]{packagePath};
        }
        //检测文件是否有包含关系
        checkIsParentFile(packages,this.getClass());
        for (String aPackage : packages) {
            scanner(aPackage);
        }
    }

    private void scanner(String aPackage) {
        File dir = getFile(aPackage,this.getClass());
        ClassLoader classLoader = getClassLoader(this.getClass());
        try {
            fillMap(dir, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fillMap(File dir, ClassLoader classLoader) throws ClassNotFoundException {
        File[] files = dir.listFiles();
        if (files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getAbsolutePath();
                    if (!filePath.endsWith(".class")) {
                        continue;
                    }
                    String classPath = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class")).replace("\\", ".");
                    Class<?> loadClass = classLoader.loadClass(classPath);
                    if (!loadClass.isAnnotationPresent(UriPri.class)) {
                        continue;
                    }
                    UriPri ulrPri = loadClass.getAnnotation(UriPri.class);
                    if (Objects.isNull(ulrPri)) {
                        continue;
                    }

                    UrlPriEntity urlPriEntity = new UrlPriEntity(ulrPri.name(), ulrPri.pri());
                    Method[] method = getMethod(loadClass);
                    for (Method md : method) {
                        MethodUrl methodUrl = md.getAnnotation(MethodUrl.class);
                        StringBuilder  params = new StringBuilder();
                        String p = "";
                        if (Objects.isNull(methodUrl)) {
                            continue;
                        }
                        Parameter[] parameters = getParameters(md);
                        for (Parameter parameter : parameters) {
                            Class<?> type = parameter.getType();
                            boolean rs = isSimpleClass(type);
                            if (rs){
                                params.append("\"").append(parameter.getName()).append("\"=").append(getTypeValue(type.getTypeName())).append(",");
                            }else if (type.isArray()){
                                params.append("\"").append(parameter.getName()).append("\"=").append("[],");
                            }else {
                                params.append("\"").append(parameter.getName()).append(":{").append(getSelfClassValue(type)).append("},");
                            }
                        }
                        p = params.substring(0,params.length());
                        MethodUrlEntity methodUrlEntity = new MethodUrlEntity(methodUrl.name(), methodUrl.url(),p);
                        map.put(methodUrlEntity, urlPriEntity);
                    }
                } else {
                    fillMap(file, classLoader);
                }
            }
        }
    }

    private Parameter[] getParameters(Method md) {
        return md.getParameters();
    }

    /**
     * 根据类型获取初始化字段
     * @param type
     * @return
     */
    private String getInitValue(String name,String type){
        return "\"" + name + "\"" + "=" + getTypeValue(type) + ",";
    }

    private boolean isSimpleClass(Type type){
        boolean flag = false;
        for (String allType : getAllTypes()) {
            if (type.getTypeName().equals(allType)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    private String getSelfClassValue(Type type) {
        Class<?> clazz = type.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder("{");
        for (Field field : fields) {
            String name = field.getName();
            String tp = field.getType().getSimpleName();
            if (clazz.isArray()){
                return "\""+name+"\" = []";
            }
            sb.append(getInitValue(name,tp));
        }
        return sb.substring(0,sb.length())+"}";
    }



}