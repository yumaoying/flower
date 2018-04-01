package com.flower.web;

import com.flower.config.PasswordEncry;
import com.flower.entity.UserInfo;
import com.flower.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(Model model) {
        List<UserInfo> ulist = userInfoService.userInfoList();
        model.addAttribute("users", ulist);
        return "userInfo/userInfo";
    }

    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd() {
        return "userInfo/userInfoAdd";
    }


    @RequestMapping("userDel")
    @RequiresPermissions("userInfo:del")
    public String userInfoDel() {
        return "userInfo/userInfoDel";
    }

    @RequestMapping("modify")
    @RequiresPermissions("userInfo:modify")
    public String userInfoEdit() {
        return "userInfo/userModify";
    }

    //@Cacheable(value = "user-key")，如果启用了redis，可开启注解
    @RequestMapping("/getUser")
    @ResponseBody
    public UserInfo getUser() {
        UserInfo userInfo = userInfoService.findByUsername("admin");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试缓存成功");
        return userInfo;
    }

    @RequestMapping("/add")
    public String add(UserInfo userInfo) {
        UserInfo user = userInfoService.findByUsername(userInfo.getUsername());
        if (user != null) {
            return "error";
        }
        userInfo.setState(new Byte("0"));
        //对用户信息进行加密存储
        PasswordEncry passwordEncry = new PasswordEncry();
        passwordEncry.encryPassword(userInfo);
        userInfoService.save(userInfo);
        return "userList";
    }

}
