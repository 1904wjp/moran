package com.moon.joyce.commons.factory.demo;

import com.moon.joyce.commons.annotation.url.MethodUrl;
import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.factory.demo.base.BaseFactory;
import com.moon.joyce.commons.factory.entity.url.MethodUrlEntity;
import com.moon.joyce.commons.factory.entity.url.UrlPriEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 15:14
 * @describe: 接口工厂
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
     * 初始化容器
     * @param ps
     * @return
     */
    public static Map<MethodUrlEntity, UrlPriEntity> init(String ps) {
        if (Objects.isNull(map)) {
            //初始化
            UrlFactory instance = getInstance();
            map = new ConcurrentHashMap<>();
            //扫描包
            instance.scannerPackage(ps);
        }
        return map;
    }

    /**
     * 扫描指定文件夹
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

    /**
     * 扫描文件
     * @param aPackage
     */
    private void scanner(String aPackage) {
        File dir = getFile(aPackage,this.getClass());
        ClassLoader classLoader = getClassLoader(this.getClass());
        try {
            fillMap(dir, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充属性
     * @param dir
     * @param classLoader
     * @throws ClassNotFoundException
     */
    private void fillMap(File dir, ClassLoader classLoader) throws ClassNotFoundException {
        File[] files = dir.listFiles();
        if (files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getAbsolutePath();
                    if (!filePath.endsWith("."+fileType)) {
                        continue;
                    }
                    String classPath = filePath.substring(filePath.indexOf(defParent), filePath.indexOf("."+fileType)).replace("\\", ".");
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
                    StringBuilder  params = new StringBuilder();
                    boolean rs;
                    String p="";
                    for (Method md : method) {
                        MethodUrl methodUrl = md.getAnnotation(MethodUrl.class);
                        if (Objects.isNull(methodUrl)) {
                            continue;
                        }
                        for (Parameter parameter : getParameters(md)) {
                            rs = isSimpleClass(parameter.getType());
                            if (rs){
                                if (parameter.getType().toString().contains("MultipartFile")){
                                    params.append("\"").append(parameter.getName()).append("\":").append("\"文件\"");
                                }else {
                                    params.append("\"").append(parameter.getName()).append("\":").append(getTypeValue(parameter.getType().getTypeName())).append(",");
                                }
                            }else if (parameter.getType().isArray()){
                                //文件类型数组
                                if (parameter.getType().toString().contains("MultipartFile")){
                                    params.append("\"").append(parameter.getName()).append("\":").append("[\"文件1\",\"文件2\"]");
                                }else {
                                    params.append("\"").append(parameter.getName()).append("\":").append("[],");
                                }
                            }else {
                                if (!getSelfClassValue(parameter.getType()).equals("{}")){
                                    params.append(getSelfClassValue(parameter.getType()));
                                }
                            }
                        }
                        p= params.toString();
                        if (params.toString().contains(",")){
                           p= params.substring(0,params.length()-1);
                        }
                        MethodUrlEntity methodUrlEntity = new MethodUrlEntity(methodUrl.name(), methodUrl.url(),p);
                        map.put(methodUrlEntity, urlPriEntity);
                    }
                } else {
                    fillMap(file, classLoader);
                }
            }
        }
    }

    //获取方法里的参数信息
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

    /**
     * 是否是基本包装类型
     * @param type
     * @return
     */
    private boolean isSimpleClass(Type type){
        boolean flag = false;
        for (String allType : getAllTypes()) {
           // System.out.println("............"+type.getTypeName());
            if (type.getTypeName().contains(allType)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 自定义类属性编辑
     * @param type
     * @return
     */
    public String getSelfClassValue(Class type) {
       // Class<?> clazz = type.getClass();
        Field[] fields = getFields(type);
        Set<String> set = new HashSet<>();
        for (Method method : getMethods(type)) {
            set.add(method.getName());
        }
        if (fields.length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder("{");
        for (Field field : fields) {
            String name = field.getName();
            String setMethodName =  "set"+name.substring(0,1).toUpperCase()+name.substring(1);
            if (!set.contains(setMethodName)){
                continue;
            }
            String tp = field.getType().getSimpleName();
            if (type.isArray()){
                return "\""+name+"\" : []";
            }
            sb.append(getInitValue(name,tp).replace("=",":"));
        }
        String sbStr = sb.toString();
        if (sbStr.contains(",")){
            sbStr =   sbStr.substring(0, sb.length() - 1);
        }
        return sbStr+"}";
    }
}
