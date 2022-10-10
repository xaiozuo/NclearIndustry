package com.weiwu.nuclearindustry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Properties;

@SpringBootTest
public class SystemTests {
    @Test
    public void testJavaEnv(){
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
    }

    @Test
    public void testSystem(){
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
    }
}
