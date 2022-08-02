package com.example.shirotemplate;

import com.example.shirotemplate.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroTemplateApplicationTests {

    @Test
    void contextLoads() {
        Md5Util md5Util = new Md5Util();
        System.out.println(md5Util.encryptPassword("123456"));

    }

}
