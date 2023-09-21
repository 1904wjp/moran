package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.factory.demo.base.BaseFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;

public class CommonUtils {
    /**
     * 获取zfbinfo.properties文件里的值
     * @param name key
     * @return
     * @throws Exception
     */
    public String getZFBinfoValue(String name) throws Exception{
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/joyce.properties"));
        String filepath = props.getProperty(name);;
        return filepath;
    }

    /**
     * 全为空
     * @param objects
     * @return
     */
    public static boolean allIsNull(Object...objects){
        int count = 0;
        if (Objects.isNull(objects[1])){
            return true;
        }
        for (int i = 1; i < objects.length; i++) {
            if (Objects.nonNull(objects[i])){
                count++;
            }
        }
        return count == 0;
    }

    /**
     * 全为空
     * @param obj
     * @return
     */
    public static boolean objArgsIsNull(Object obj){
        int count = 0;
        Field[] fields = obj.getClass().getDeclaredFields();
        Class<?> superclass = obj.getClass().getSuperclass();
        while (superclass!=null){
           fields = BaseFactory.addFields(fields,superclass.getDeclaredFields());
            superclass = superclass.getSuperclass();
        }
        for (Field declaredField : fields) {
            if (declaredField.getName().equalsIgnoreCase("SerialVersionUID")){
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("get");
            sb.append(declaredField.getName().substring(0, 1).toUpperCase());
            sb.append(declaredField.getName().substring(1));
            Method method = null;
            try {
                method = obj.getClass().getMethod(sb.toString());
                Object invoke = method.invoke(obj);
                if (invoke==null){
                    count++;
                }
              if (invoke!=null){
                  System.out.println("name:"+declaredField.getName()+",value:"+invoke.toString());
              }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return count == 0;
    }

    /**
     * 全不为空
     * @param objects
     * @return
     */
    public static boolean allNonNull(Object...objects){
        return !allIsNull(objects);
    }
}
