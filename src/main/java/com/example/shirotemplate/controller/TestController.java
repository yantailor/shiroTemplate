package com.example.shirotemplate.controller;

import com.example.shirotemplate.bean.R;
import com.example.shirotemplate.entity.User;
import com.example.shirotemplate.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yantailor
 * on 2022/8/1 21:11 @Version 1.0
 */
@RestController
@RequestMapping("/test/user")
public class TestController {

    @Autowired
    UserService userService;
    //添加新用户
    @PostMapping("/register")
    public R register(User user){
        boolean b = userService.register(user);
        if(!b){
            return R.error().data("registerState","注册失败");
        }
        return R.ok().data("registerState","注册成功");
    }

    @PostMapping("/login")
    public R login(User user){
        String token = userService.login(user);
        if(token == null){
            return R.error().data("loginState","登录失败,账号或者密码错误");
        }
        return R.ok().data("loginState","登录成功").data("token",token);
    }

    @RequiresPermissions("LV3")
    @PostMapping("/requirePermission")
    public R testPermission(){
        return R.ok();
    }

}
