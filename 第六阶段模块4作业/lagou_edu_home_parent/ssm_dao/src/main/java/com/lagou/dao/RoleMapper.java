package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface RoleMapper {

    /*查询所有角色 条件查询*/
    public List<Role> findAllRole(Role role);

    /*根据id查询角色关联的菜单信息id*/
    public List<Integer> findMenuByRoleId(Integer roleId);


    /*根据roleid清空中间表关联关系*/
    public void deleteRoleContextMenu(Integer rid);

    /*为角色分配菜单信息*/
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    /*删除角色*/
   public void deleteRole(Integer id);

   /*角色拥有的资源分类信息*/
    public List<ResourceCategory> findResourceCategoryByRoleId(Integer id);

    /*角色拥有的资源信息*/
    public List<Resource> findResourceByResourceCategoryId(Integer id);

    /*角色ID 删除角色与资源的关联关系*/
    public void deleteRoleResourceByRoleId(Integer id);

    /*角色分配资源*/
    public void addRoleResource(RoleResourceRelation roleResourceRelation);






}
