package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectTests {

    @Test
    public void testObject(){
        String string = new String("String");
        Integer integer = new Integer(100);
        OpticalSatellite opticalSatellite = new OpticalSatellite();
        RadarSatellite radarSatellite = new RadarSatellite();
        doObject(string);
        doObject(integer);
        doObject(opticalSatellite);
        doObject(radarSatellite);
    }

    private Object doObject(Object object){
        Class<?> aClass = object.getClass();
        System.out.println(aClass.getName() + " " + aClass.getSimpleName());
        System.out.println(object.toString());
        return object;
    }
}
