package com.flower.controller;

import com.flower.config.PasswordEncry;
import com.flower.entity.SysUser;
import com.flower.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yumaoying on 2018/3/24.
 * 系统管理员
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //用户管理页面
    @RequestMapping
    public String userPage() {
        return "sysUser/sysUsers";
    }

    //个人信息页面
    @RequestMapping("/loadUser")
    public String loadSysUser() {
        return "sysUser/userSelf";
    }

    //个人信息页面
    @RequestMapping("/findUser")
    @ResponseBody
    public SysUser findById(Integer uid) {
        if (!StringUtils.isEmpty(uid)) {
            SysUser user = sysUserService.findByUid(uid);
            return user;
        } else {
            SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
            return user;
        }
    }

    //个人信息-重置密码
    @RequestMapping("/newPassword")
    @ResponseBody
    public String newPassword(SysUser user) {
        try {
            //密码加密存储
            PasswordEncry passwordEncry = new PasswordEncry();
            passwordEncry.encryPassword(user);
            if (sysUserService.editPassword(user.getUid(), user.getPassword(), user.getSalt()) > 0)
                return "success";
            else return "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //根据id查找用户
    @RequestMapping("/findByid")
    @ResponseBody
    public SysUser findUser(Integer uid) {
        return sysUserService.findByUid(uid);
    }

    //修改用户信息
    @RequestMapping("/edit")
    @ResponseBody
    public String edit(SysUser sysUser) {
        try {
            sysUserService.edit(sysUser.getUid(), sysUser.getUsername(), sysUser.getName(), sysUser.getState());
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("constraint"))
                return "fail";
            else
                return "error";
        }
    }

    //添加用户
    @RequestMapping("/add")
    @ResponseBody
    public String add(SysUser sysUser) {
        SysUser user = sysUserService.findByUsername(sysUser.getUsername());
        if (user != null) {
            return "fail";
        }
        //对用户信息进行加密存储
        try {
            PasswordEncry passwordEncry = new PasswordEncry();
            passwordEncry.encryPassword(sysUser);
            sysUserService.save(sysUser);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //删除用户
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Integer id) {
        try {
            sysUserService.del(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //@Cacheable(value = "user-key")，如果启用了redis，可开启注解
    //用户分页查询
    @RequestMapping("/users")
    @ResponseBody
    public Map<String, Object> get(SysUser sysUser, String draw,
                                   @RequestParam(required = false, defaultValue = "0") int start,
                                   @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.ASC, "uid");//按用户id降序排
        Pageable pageable = new PageRequest(start / length, length, sort);
        Page page = sysUserService.getUserInfoPage(sysUser, pageable);
        map.put("draw", draw);
        map.put("recordsTotal", page.getTotalElements());
        map.put("recordsFiltered", page.getTotalElements());
        map.put("data", page.getContent());
        return map;
    }

    //分配角色
    @RequestMapping(value = "/addUserRole")
    @ResponseBody
    public String addRole(Integer uid, @RequestParam("roleId") Integer[] roleId) {
        try {
            sysUserService.addUserRole(uid, roleId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
