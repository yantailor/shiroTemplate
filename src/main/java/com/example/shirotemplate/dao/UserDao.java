package com.example.shirotemplate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shirotemplate.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yantailor
 * on 2022/8/1 21:33 @Version 1.0
 */
@Mapper
public interface UserDao {
    @Insert("insert into user (username,password,role_id) values (#{username},#{password},#{roleId})")
    void register(String username,String password,int roleId);

    @Select("SELECT u.`user_id`,r.role_id,r.role_name role,p.permission_name VoPermission FROM role r JOIN role_permission rp ON r.`role_id` = rp.`role_id` JOIN permission p ON rp.`permission_id` = p.`permission_id` \n" +
            "JOIN USER u ON u.`role_id` = r.`role_id`  WHERE username = #{username} and password = #{password}")
    List<User> login(String username , String password);
}
