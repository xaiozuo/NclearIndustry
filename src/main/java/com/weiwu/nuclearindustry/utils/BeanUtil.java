package com.weiwu.nuclearindustry.utils;


import lombok.SneakyThrows;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    //NOTE: new object instance in method will generate strong coupling,
    //so you should make it a parameter.
    @SneakyThrows
    public static Object build(XmlParser xmlParser, File file, Object object){
        Class<?> aClass = object.getClass();
        HashMap<String, String> hashMap = xmlParser.parseXml(file);
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
            String field = entry.getKey().split(" ")[1];
            Method declaredMethod = aClass.getDeclaredMethod("set" + field, String.class);
            declaredMethod.invoke(object, entry.getValue());
        }
        return object;
    }

}
