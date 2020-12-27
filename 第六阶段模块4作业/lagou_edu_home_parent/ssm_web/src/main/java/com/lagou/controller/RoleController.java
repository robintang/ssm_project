package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /*查询所有角色 条件查询*/
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){
        List<Role> roleList = roleService.findAllRole(role);
        return new ResponseResult(true,200,"查询成功",roleList);
    }

    @Autowired
    private MenuService menuService;

    /*查询所有的父子菜单信息*/
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid(){
        //-1表示查询所有的父子级菜单
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);

        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",menuList);
        return new ResponseResult(true,200,"响应成功",map);
    }

    /*根据id查询角色关联的菜单信息id*/
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(@RequestParam Integer roleId){
        List<Integer> list = roleService.findMenuByRoleId(roleId);
        return new ResponseResult(true,200,"响应成功",list);
    }

   /*为角色分配菜单*/
    @RequestMapping("/roleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo){
        roleService.roleContextMenu(roleMenuVo);
        return new ResponseResult(true,200,"响应成功",null);
    }

    /**
     * 删除角色 * */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id){
        roleService.deleteRole(id);
        return new ResponseResult(true,200,"响应成功","");
    }

    /*当前角色拥有的 资源信息*/
    @RequestMapping("/findResourceListByRoleId")
    public ResponseResult findResourceListByRoleId(Integer roleId){
        List<ResourceCategory> categories = roleService.findResourceListByRoleId(roleId);
        return new ResponseResult(true,200,"响应成功",categories);
    }

    /*为角色分配菜单*/
    @RequestMapping("/roleContextResource")
    public ResponseResult roleContextResource(@RequestBody RoleResourceVo roleResourceVo){
        roleService.roleContextResource(roleResourceVo);
        return new ResponseResult(true,200,"响应成功",null);
    }


}
