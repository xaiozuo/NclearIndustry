package com.weiwu.nuclearindustry.utils;


import lombok.SneakyThrows;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    //NOTE: new object instance in method will generate strong coupling,
    //so you should make it a parameter.

    public static Method findMethod(Class<?> clazz, String methodName){
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for(Method method : declaredMethods){
            if(method.getName().contains("set" + methodName)){
                return method;
            }
        }
        return null;
    }

    @SneakyThrows
    public static Object build(XmlParser xmlParser, File file, Object object){
        Class<?> aClass = object.getClass();
        HashMap<String, String> hashMap = xmlParser.parseXml(file);
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
            String field = entry.getKey().split(" ")[1];
            Method declaredMethod = findMethod(aClass, field);
            if(null != declaredMethod){
                declaredMethod.invoke(object, entry.getValue());
            }
        }
        Method setDone = aClass.getDeclaredMethod("setDone", Boolean.class);
        setDone.invoke(object, true);
        return object;
    }

}
