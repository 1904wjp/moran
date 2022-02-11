package com.moon.joyce.commons.utils.enums;

import org.apache.commons.compress.utils.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

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
