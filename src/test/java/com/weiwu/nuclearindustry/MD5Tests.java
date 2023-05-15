package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.utils.MD5Util;
import org.junit.jupiter.api.Test;

public class MD5Tests {

    @Test
    public void testMd5(){
        String name = "weir";
        String str = "12345678";
        System.out.println(MD5Util.md5(name));
        System.out.println(MD5Util.md5(str));
    }
}
