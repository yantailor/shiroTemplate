package com.example.shirotemplate.entity;

import lombok.Data;

/**
 * Created by yantailor
 * on 2022/8/1 22:45 @Version 1.0
 */

@Data
public class User {
    private String username;
    private String password;
    private int roleId = 1;
    private String role;
    private String VoPermission;
    private String[] permission;
}
