package com.moon.joyce.commons.factory.demo.base;

import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
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
    "String","String","",
    "short","Short",0,
    "int","Integer",0,
    "long","Long",0L,
    "float","Float",0.0f,
    "double","Double",0.0,
    "boolean","Boolean",false,
    "char","Character",0};
    //下标基准
    private static final int INDEX = 1;
    protected static final String defParent ="com";
    protected static final String fileType ="class";
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
            throw new JoyceException("配置包不可含有null或者''");
        }
        if (packages.length < 2) {
            return;
        }
        for (int i = 0; i < packages.length; i++) {
            File f1 = getFile(packages[i],clazz);
            for (int j = i + 1; j < packages.length; j++) {
                File f2 = getFile(packages[j],clazz);
                if (f1.equals(f2.getParentFile()) || f2.equals(f1.getParentFile())) {
                    throw new JoyceException(packages[i] + "与" + packages[j] + "是相互包含的关系，选择一个即可");
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

    /**
     * 加载路径方法
     * @param filePath
     * @param classLoader
     * @param start
     * @param end
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> loadClassByPath(String filePath,ClassLoader classLoader,String start ,String end) throws ClassNotFoundException {
        start = Objects.isNull(start)?defParent:start;
        end = Objects.isNull(end)?fileType:end;
        String classPath = filePath.substring(filePath.indexOf(start), filePath.indexOf("."+end)).replace("\\", ".");
        return  classLoader.loadClass(classPath);
    }

    List<File> list ;
    /**
     * 获取class文件集合
     * @param file
     * @return
     */
    protected List<File> getClassFile(File file){
        if (list==null){
           list = new ArrayList<>();
        }
        File[] files = file.listFiles();
        if (files==null){
            return null;
        }
        for (File f : files) {
            if (f.isFile()){
                if (f.getAbsolutePath().endsWith("."+fileType)){
                    list.add(f);
                }
            }else {
              getClassFile(f);
            }
        }
        return list;
    }


    /**
     * 根据路径获取对应文件
     * @param packagePath
     * @return
     */
    private File getFile(String packagePath,ClassLoader classLoader) {
        String rePackageName = packagePath.replace(".", "/");
        URL resource = classLoader.getResource(rePackageName);
        return new File(resource.getFile());
    }
    /**
     * 获取class文件集合
     * @param path
     * @return
     */
    protected List<File> getClassFile(String path,ClassLoader classLoader){
        return getClassFile(getFile(path,classLoader));
    }
    /**
     * 获取class文件集合
     * @param path
     * @return
     */
    List<File> allList = new ArrayList<>();
    protected List<File> getClassFileByPack(String path,ClassLoader classLoader){
        List<String> paths = StringsUtils.strToList(path);
        if (paths.isEmpty()){
            return null;
        }
        for (String p : paths) {
            List<File> classFile = getClassFile(p, classLoader);
            if (classFile!=null){
                allList.addAll(classFile);
            }
        }
        return allList;
    }

    /**
     * 获取类所有属性
     * @param loadClass
     * @return
     */
    protected Field[] getFields(Class<?> loadClass) {
        Field[] fields = loadClass.getDeclaredFields();
        Class<?> superclass = loadClass.getSuperclass();
        while (Objects.nonNull(superclass)) {
          Field[] superclassFields = superclass.getDeclaredFields();
          fields = addFields(fields, superclassFields);
          superclass = superclass.getSuperclass();
        }
        return fields;
    }

    /**
     * 自动创建工厂专用获取类所有属性
     * @param loadClass
     * @return
     */
    protected Field[] getFieldsByAutoCreate(Class<?> loadClass) {
        Field[] fields = loadClass.getDeclaredFields();
        Class<?> superclass = loadClass.getSuperclass();
        while (Objects.nonNull(superclass)) {
            if (Objects.nonNull(superclass.getAnnotation(Table.class))) {
                Field[] superclassFields = superclass.getDeclaredFields();
                fields = addFields(fields, superclassFields);
            }
            superclass = superclass.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取类所有属性
     * @param loadClass
     * @return
     */
    protected Method[] getMethods(Class<?> loadClass) {
        Method[] methods = loadClass.getDeclaredMethods();
        Class<?> superclass = loadClass.getSuperclass();
        while (Objects.nonNull(superclass)) {
            Method[] method2s = superclass.getDeclaredMethods();
            methods =  addMethods(methods,method2s);
            superclass = superclass.getSuperclass();
        }
        return methods;
    }

    /**
     * 获取类所有属性
     * @param loadClass
     * @return
     */
    protected Method getMethod(Class<?> loadClass,String methodName)  {
        Method method = null;
        try {
         method = loadClass.getMethod(methodName);
        if (method!=null){
            return method;
        }
        Class<?> superclass = loadClass.getSuperclass();
        while (Objects.nonNull(superclass)) {
              method = superclass.getMethod(methodName);
             if (method!=null){
                break;
             }
            superclass = superclass.getSuperclass();
        }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }


    /**
     * 计算总属性集合
     * @param fs1
     * @param fs2
     * @return
     */
    public static Field[] addFields(Field[] fs1, Field[] fs2) {
        if (Objects.isNull(fs1) || fs1.length == 0) {
            return fs2;
        }
        if (Objects.isNull(fs2) || fs2.length == 0) {
            return fs1;
        }
        Field[] fields = new Field[fs1.length + fs2.length];
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i < fs1.length) {
                fields[i] = fs1[i];
            } else {
                fields[i] = fs2[index++];
            }
        }
        return fields;
    }
    /**
     * 计算总属性集合
     * @param fs1
     * @param fs2
     * @return
     */
    public static  Field[] addFields(Field[] fs1, Field fs2) {
        if (Objects.isNull(fs2) ) {
            return fs1;
        }
        Field[] fields = new Field[fs1.length + 1];
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i < fs1.length) {
                fields[i] = fs1[i];
            } else {
                fields[i] = fs2;
            }
        }
        return fields;
    }

    /**
     * 计算总方法集合
     * @param fs1
     * @param fs2
     * @return
     */
    private Method[] addMethods(Method[] fs1, Method[] fs2) {
        if (Objects.isNull(fs1) || fs1.length == 0) {
            return fs2;
        }
        if (Objects.isNull(fs2) || fs2.length == 0) {
            return fs1;
        }
        Method[] fields = new Method[fs1.length + fs2.length];
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i < fs1.length) {
                fields[i] = fs1[i];
            } else {
                fields[i] = fs2[index++];
            }
        }
        return fields;
    }


}
