package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;

import java.util.List;

public interface UserService {

    /*查询所有用户 分页*/
    public PageInfo findAllUserByPage(UserVo userVo);

    /*修改用户状态*/
    public void updateUserStatus(int id, String status);

    /*用户登陆(根据用户名查询用户信息)*/
    public User login(User user);

    /*根据用户id查询关联的角色信息*/
    public List<Role> findUserRolationById(Integer id);


    /*用户关联角色*/
    public void userContextRole(UserVo userVo);

    /*获取用户权限*/
    ResponseResult getUserPermissions(Integer id);
}

