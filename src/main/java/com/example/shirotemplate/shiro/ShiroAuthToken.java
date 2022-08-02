package com.example.shirotemplate.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by yantailor
 * on 2022/8/1 17:56 @Version 1.0
 */
public class ShiroAuthToken implements AuthenticationToken {
    private String token;
    public ShiroAuthToken(String token){
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
