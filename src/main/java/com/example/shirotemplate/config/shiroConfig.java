package com.example.shirotemplate.config;

import com.example.shirotemplate.shiro.CustomerRealm;
import com.example.shirotemplate.shiro.ShiroFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by yantailor
 * on 2022/8/1 17:30 @Version 1.0
 */
@Configuration
public class shiroConfig {

    /*
    * 创建安全管理器,使用自定义Realm以及关闭Session
    * */
    @Bean
    public DefaultWebSecurityManager securityManager(CustomerRealm customerRealm){
        //创建安全管理器
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //设置管理器使用自定义的realm
        manager.setRealm(customerRealm);

        //使用JWT需要禁用Session
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    /*
    * 添加拦截器和配置拦截规则
    * */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);

        //添加自定义的拦截器
        Map<String, Filter> filters = new HashMap<>(2);
        //  添加 shiroAuthFilter 的拦截器，不要使用 Spring 来管理 Bean
        filters.put("authFilter", new ShiroFilter());
        factoryBean.setFilters(filters);

        LinkedHashMap<String, String> filterChainDefinitions = new LinkedHashMap<>(4);
        filterChainDefinitions.put("/test/user/login", "anon");
        filterChainDefinitions.put("/test/user/register", "anon");
        filterChainDefinitions.put("/**", "authFilter");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        return factoryBean;
    }
}
