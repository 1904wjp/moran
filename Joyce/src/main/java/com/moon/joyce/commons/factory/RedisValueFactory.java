package com.moon.joyce.commons.factory;

import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.poi.ss.formula.functions.T;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/10-- 11:25
 * @describe:
 */
public class RedisValueFactory {
    /**
     * 创建容器
     */
    private static Map<String, Object> map = null;

    /**
     * 获取map实例
     *
     * @return
     */
    public static void main(String[] args) {
        Map<String, Object> instance = getInstance();
        System.out.println("---------------"+map.toString());
        for (Map.Entry<String, Object> entry : instance.entrySet()) {
            System.out.println(entry.getKey()+"::"+entry.getValue());
        }
    }


    /**
     * 实例加载，获取实例
     * @return
     */
    public static Map<String, Object> getInstance() {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
            try {
                scanner("com/moon/joyce/example/service/impl");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    /*public static Object getBean(String key) {
        Map<String, Object> map = getInstance();
        boolean rs = map.containsKey(key);
        if (!rs) {
            throw new JoyceException("this key not found object");
        }
        return  map.get(key);
    }*/

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    public static Object getBean(String key) {
        Map<String, Object> map = getInstance();
        boolean rs = map.containsKey(key);
        if (!rs) {
            throw new JoyceException("this key not found object");
        }
        return map.get(key);
    }



    /**
     * 扫描包下特定注解的类
     *
     * @param packageName
     * @return
     */
    private static Map<String, Object> scanner(String packageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String rePackageName = packageName.replace(".", "/");
        ClassLoader classLoader = RedisValueFactory.class.getClassLoader();
        URL resource = classLoader.getResource(rePackageName);
        File dir = new File(resource.getFile());
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file : files) {
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".class")){continue;}
                String classPath = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class"));
                classPath = classPath.replace("\\", ".");
                Class<?> loadClass = classLoader.loadClass(classPath);
                if (loadClass.isAnnotationPresent(RedisValueComponet.class)){
                    String key = loadClass.getAnnotation(RedisValueComponet.class).value();
                    Constructor<?> declaredConstructor = loadClass.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    map.put(key,loadClass.newInstance());
                }
            }
        }
        return map;
    }

}
