package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        //1.清空中间表关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //2。为角色分配菜单
        for (Integer mid : roleMenuVo.getMenuIdList()) {

            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());
            //封装数据
            role_menu_relation.setCreatedTime(new Date());
            role_menu_relation.setUpdatedTime(new Date());
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");
            roleMapper.roleContextMenu(role_menu_relation);
        }


    }

    @Override
    public void deleteRole(Integer id) {
        // 清空中间表
        roleMapper.deleteRoleContextMenu(id);
        roleMapper.deleteRole(id);
    }

    @Override
    public List<ResourceCategory> findResourceListByRoleId(Integer id) {

        List<ResourceCategory> categoryList = roleMapper.findResourceCategoryByRoleId(id);
        for (ResourceCategory category : categoryList) {
            List<Resource> resourceList = roleMapper.findResourceByResourceCategoryId(category.getId());
            category.setResourceList(resourceList);
        }
        return categoryList;
    }

    @Override
    public void roleContextResource(RoleResourceVo roleResourceVo) {
        // 清空中间表
        roleMapper.deleteRoleResourceByRoleId(roleResourceVo.getRoleId());
        //添加表
        for (Integer id : roleResourceVo.getResourceIdList()) {
            RoleResourceRelation relation = new RoleResourceRelation();
            relation.setResourceId(id);
            relation.setRoleId(roleResourceVo.getRoleId());
            Date date = new Date();
            relation.setUpdatedTime(date);
            relation.setCreatedTime(date);
            relation.setUpdatedBy("system");
            relation.setCreatedBy("system");
            roleMapper.addRoleResource(relation);
        }
    }
}
