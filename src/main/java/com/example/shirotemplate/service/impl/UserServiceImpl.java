package com.example.shirotemplate.service.impl;

import com.example.shirotemplate.dao.UserDao;
import com.example.shirotemplate.entity.User;
import com.example.shirotemplate.service.UserService;
import com.example.shirotemplate.utils.token.TokenUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yantailor
 * on 2022/8/1 21:20 @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenUtil tokenUtil;
    private static final String salt = "yantailor";



    @Override
    public boolean register(User user) {
        try {
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt);
            user.setPassword(md5Hash.toHex());
            userDao.register(user.getUsername(),user.getPassword(),user.getRoleId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String login(User user) {
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt);
        user.setPassword(md5Hash.toHex());
        List<User> login = userDao.login(user.getUsername(), user.getPassword());

        if(login != null){
            String[] permissions = new String[login.size()];
            int temp = 0;
            for (User u : login){
                permissions[temp] = String.valueOf(u.getVoPermission());
                temp++;
            }
            user.setPermission(permissions);
            user.setRole(login.get(0).getRole());
            String token = tokenUtil.createToken(user);
            return token;
        }
        return null;
    }
}
