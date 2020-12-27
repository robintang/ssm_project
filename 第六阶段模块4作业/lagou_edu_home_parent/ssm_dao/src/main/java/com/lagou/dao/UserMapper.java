package com.lagou.dao;

import com.lagou.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /*查询所有用户*/
    public List<User> findAllUserByPage(UserVo userVo);


    /* 修改用户状态 */
    public void updateUserStatus(@Param("id") int id, @Param("status") String status);

    /*用户登陆(根据用户名查询用户信息)*/
    public User login(User user);



    /*根据用户id清空中间表*/
    public void deleteUserContextRole(Integer userId);

    /*分配角色*/
    public void userContextRole(User_Role_relation user_role_relation);


    /*根据用户id查询关联的角色信息*/
    public List<Role> findUserRolationById(Integer id);


    /* 根据角色id,查询角色拥有的顶级菜单信息 */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);


    /* 根据PID 查询子菜单信息*/
    public List<Menu> findSubMenuByPid(int pid);


    /* 获取用户拥有的资源权限信息 */
    public List<Resource> findResourceByRoleId(List<Integer> ids);

}
