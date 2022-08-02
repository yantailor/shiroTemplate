package com.example.shirotemplate.service;

import com.example.shirotemplate.entity.User;

/**
 * Created by yantailor
 * on 2022/8/1 21:19 @Version 1.0
 */
public interface UserService {
    boolean register(User user);

    String login(User user);
}
