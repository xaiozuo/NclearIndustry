package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.utils.XmlParser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.*;

@SpringBootTest
public class XmlParserTests {

    @Test
    public void testGF3() throws DocumentException {
        String metaPath = SystemConfig.SOURCE_PATH + File.separator + "202208290059070" + File.separator + "metadata";
        String filePath = metaPath + File.separator + "GF3" + File.separator
                + "GF3_KAS_FSII_020743_E115.8_N28.1_20200718_L1A_HHHV_L10004935216" + File.separator
                + "GF3_KAS_FSII_020743_E115.8_N28.1_20200718_L1A_HHHV_L10004935216.meta.xml";
        XmlParser xmlParser = new XmlParser();
        HashMap<String, String> hashMap = xmlParser.parseXml(new File(filePath));
        System.out.println(hashMap);
    }

    @Test
    public void testGF124567() throws DocumentException {
        String metaPath = SystemConfig.SOURCE_PATH + File.separator + "202208290059070" + File.separator + "metadata";
//        String filePath = metaPath + File.separator + "GF124567" + File.separator
//                + "GF1_PMS1_E91.7_N27.2_20210103_L1A0005366574" + File.separator
//                + "GF1_PMS1_E91.7_N27.2_20210103_L1A0005366574.xml";
        String filePath = metaPath + File.separator + "GF124567" + File.separator
                + "GF1B_PMS_E101.2_N41.3_20200709_L1A1227834890" + File.separator
                + "GF1B_PMS_E101.2_N41.3_20200709_L1A1227834890.xml";
        XmlParser xmlParser = new XmlParser();
        HashMap<String, String> hashMap = xmlParser.parseXml(new File(filePath));
        System.out.println(hashMap);
    }
}
