package com.lagou.service;

import com.lagou.domain.*;

import java.util.List;

public interface RoleService {

    /*查询所有角色 条件查询*/
    public List<Role> findAllRole(Role role);

    /*根据id查询角色关联的菜单信息id*/
    public List<Integer> findMenuByRoleId(Integer roleId);

    /*为角色分配菜单信息*/
    public void roleContextMenu(RoleMenuVo roleMenuVo);

    /*删除角色*/
    public void deleteRole(Integer id);

    /*当前角色拥有的 资源信息*/
    public List<ResourceCategory> findResourceListByRoleId(Integer id);

    /*为角色分配菜单*/
    public void roleContextResource(RoleResourceVo roleResourceVo);
}
