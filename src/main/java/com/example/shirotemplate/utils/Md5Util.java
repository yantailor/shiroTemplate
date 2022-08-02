package com.example.shirotemplate.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by yantailor
 * on 2022/8/1 21:16 @Version 1.0
 */
public class Md5Util {
    private static final String salt = "yantailor";

    public String encryptPassword(String password){
        Md5Hash md5Hash = new Md5Hash(password, salt);
        return md5Hash.toHex();
    }
}
