package com.example.shirotemplate.shiro;

import com.example.shirotemplate.entity.User;
import com.example.shirotemplate.utils.token.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by yantailor
 * on 2022/8/1 17:29 @Version 1.0
 */
@Service
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    TokenUtil tokenUtil;

    @Override
     public boolean supports(AuthenticationToken authenticationToken) {
        // 指定当前 authenticationToken 需要为 ShiroAuthToken 的实例
         return authenticationToken instanceof ShiroAuthToken;
     }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = tokenUtil.verifyToken(principalCollection.toString());

        if(user == null){
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (!user.getRole().isEmpty()){
            info.addRole(user.getRole());
        }
        if(user.getPermission().length>0){
            info.addStringPermissions(Arrays.asList(user.getPermission()));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        ShiroAuthToken shiroAuthToken = (ShiroAuthToken) authenticationToken;
        String token = (String) shiroAuthToken.getCredentials();

        User user = tokenUtil.verifyToken(token);
        if(user==null){
            throw new AuthenticationException("token无效");
        }
        return new SimpleAuthenticationInfo(token,token,this.getName());
    }
}
