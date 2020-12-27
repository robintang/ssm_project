package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo) {

        PageInfo pageInfo = userService.findAllUserByPage(userVo);

        return new ResponseResult(true, 200, "分页多条件查询成功", pageInfo);

    }

    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(@RequestParam int id, @RequestParam String status) {
        if ("ENABLE".equalsIgnoreCase(status)) {
            status = "DISABLE";
        } else {
            status = "ENABLE";
        }
        userService.updateUserStatus(id, status);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成 功", status);
        return responseResult;
    }

    /*用户登陆*/
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) {
        User login = userService.login(user);
        if (login != null) {
            //保存用户id 及access takon 到session中
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token", access_token);
            session.setAttribute("user_id", login.getId());

            //将查询出得信息响应前台
            HashMap<String, Object> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("user_id", login.getId());
            return new ResponseResult(true, 200, "登陆成功", map);
        } else {
            return new ResponseResult(true, 400, "用户名密码错误", null);
        }
    }


    /*获取用户拥有的角色*/
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(int id) {
        List<Role> roleList = userService.findUserRolationById(id);
        return new ResponseResult(true, 200, "分配角色回显成功", roleList);
    }

    /*分配角色*/
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){
        userService.userContextRole(userVo);
        return new ResponseResult(true,200,"分配角色成功",null);
    }

    /**
     * 获取用户权限 * */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){
        //获取请求头中的 token
        String token = request.getHeader("Authorization");

        //获取session中的access_token
        HttpSession session = request.getSession();
        String access_token = (String)session.getAttribute("access_token");

        //判断
        if(token.equals(access_token))
        {
            int user_id = (Integer)session.getAttribute("user_id");
            return userService.getUserPermissions(user_id);
         }
        else
        {
            return new ResponseResult(false,400,"获取失败","");
        }


    }

    }








