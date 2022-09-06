package com.moon.joyce.commons.factory.demo.base;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/05-- 15:08
 * @describe:
 */
public class BaseFactory {

    protected Object[] typeName = {
    "byte","Byte",0,
    "short","Short",0,
    "int","Integer",0,
    "long","Long",0L,
    "float","Float",0.0f,
    "double","Double",0.0,
    "boolean","Boolean",false,
    "char","Character",0};

    private static final int index = 1;

    /**
     * 获取类型的初始值
     * @param type
     * @return
     */
    protected Object getTypeValue(String type){
        Object o = null;
        for (int i = 0; i < typeName.length; i++) {
            if ((i+index)%3 == 0){
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
            if ((i+index)%3 == 0){
                continue;
            }
            set.add(typeName[i].toString());
        }
        return set;
    }


    protected Method[] getMethod(Class<?> loadClass) {
        return loadClass.getDeclaredMethods();
    }

    protected Type[] getType(Method md){
        return md.getGenericParameterTypes();
    }

}
