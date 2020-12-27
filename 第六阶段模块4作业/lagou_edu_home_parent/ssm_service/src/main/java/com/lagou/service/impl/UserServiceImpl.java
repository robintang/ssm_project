package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> userList = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        userMapper.updateUserStatus(id,status);
    }

    @Override
    public User login(User user) {
        User user1 = userMapper.login(user);
        try {
            if (user1 != null && Md5.verify(user.getPassword(),"lagou",user1.getPassword())){
                return user1;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> findUserRolationById(Integer id) {
        return userMapper.findUserRolationById(id);
    }

    @Override
    public void userContextRole(UserVo userVo) {
        userMapper.deleteUserContextRole(userVo.getUserId());
        for (Integer integer : userVo.getRoleIdList()) {
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(integer);

            user_role_relation.setCreatedTime(new Date());
            user_role_relation.setUpdatedTime(new Date());

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);

        }
    }

    /*获取用户权限*/
    @Override
    public ResponseResult getUserPermissions(Integer id) {
        //1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRolationById(id);

        //2.获取角色id 保存到list中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }

        //3.根据角色id查询父菜单
        List<Menu> menuList = userMapper.findParentMenuByRoleId(roleIds);

        //4.查询父菜单关联的子菜单
        for (Menu menu : menuList) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }

        //5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        //6封装数据

        Map<String,Object> map = new HashMap<>();
        map.put("menuList",menuList); //menuList: 菜单权限数据
        map.put("resourceList",resourceList);
        // resourceList: 资源权限数据
        ResponseResult result = new ResponseResult(true,200,"响应成功",map);

        return null;
    }
}
