package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.utils.XmlParser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BeanTests {

    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @Test
    public void testOpSat() throws DocumentException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String metaPath = SystemConfig.SOURCE_PATH + File.separator + "202208290059070" + File.separator + "metadata";
        String filePath = metaPath + File.separator + "GF124567" + File.separator
                + "GF1_PMS1_E91.7_N27.2_20210103_L1A0005366574" + File.separator
                + "GF1_PMS1_E91.7_N27.2_20210103_L1A0005366574.xml";
        XmlParser xmlParser = new XmlParser();
        OpticalSatellite opticalSatellite = new OpticalSatellite();
        Class<? extends OpticalSatellite> aClass = opticalSatellite.getClass();
        HashMap<String, String> hashMap = xmlParser.parseGF124567XML(filePath);
        for (Map.Entry<String, String> entry : hashMap.entrySet()){
            String field = entry.getKey().split(" ")[1];
            Method declaredMethod = aClass.getDeclaredMethod("set" + field, String.class);
            declaredMethod.invoke(opticalSatellite, entry.getValue());
        }
        opticalSatelliteRepository.save(opticalSatellite);
    }

    @Test
    public void testMonitor(){
        System.out.println(File.separator);
        System.out.println(File.separator.toString());
    }
}
