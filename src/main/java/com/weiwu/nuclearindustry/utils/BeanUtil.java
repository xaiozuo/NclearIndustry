package com.weiwu.nuclearindustry.utils;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    public OpticalSatellite buildOpSat(File file) throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        XmlParser xmlParser = new XmlParser();
        OpticalSatellite opticalSatellite = new OpticalSatellite();
        Class<? extends OpticalSatellite> aClass = opticalSatellite.getClass();
        HashMap<String, String> hashMap = xmlParser.parseGF124567XML(file);
        for (Map.Entry<String, String> entry : hashMap.entrySet()){
            String field = entry.getKey().split(" ")[1];
            Method declaredMethod  = aClass.getDeclaredMethod("set" + field, String.class);
            declaredMethod.invoke(opticalSatellite, entry.getValue());
        }
        return opticalSatellite;
    }

    public RadarSatellite buildRaSat(File file) throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        XmlParser xmlParser = new XmlParser();
        RadarSatellite radarSatellite = new RadarSatellite();
        Class<? extends RadarSatellite> aClass = radarSatellite.getClass();
        HashMap<String, String> hashMap = xmlParser.parseGF3XML(file);
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
            String field = entry.getKey().split(" ")[1];
            Method declaredMethod = aClass.getDeclaredMethod("set" + field, String.class);
            declaredMethod.invoke(radarSatellite, entry.getValue());
        }
        return radarSatellite;
    }

}
