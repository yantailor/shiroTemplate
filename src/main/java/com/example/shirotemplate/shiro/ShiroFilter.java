package com.example.shirotemplate.shiro;


import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yantailor
 * on 2022/8/1 17:46 @Version 1.0
 */
public class ShiroFilter extends BasicHttpAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private String token;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(this.getAuthzHeader(request) !=null){
            try {
                executeLogin(request, response);
                return true;
            }catch (AuthorizationException authorizationException){
                throw new AuthorizationException("权限不足");
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else {
            //不存在token直接返回未授权
            return false;
        }
    }

    @Override
    protected String getAuthzHeader(ServletRequest request) {
        try {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            this.token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
            return this.token;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        this.getSubject(request,response).login(new ShiroAuthToken(this.token));
        return true;
    }
}
