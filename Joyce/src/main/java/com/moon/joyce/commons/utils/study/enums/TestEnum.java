package com.moon.joyce.commons.utils.study.enums;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum TestEnum {
    TEST;
    public Enum getTest(){
        return TEST;
    }
}
class Test{
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<TestEnum> declaredConstructor = TestEnum.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        TestEnum testEnum = declaredConstructor.newInstance();
    }
}
