package com.flower.controller;

import com.flower.entity.SysRole;
import com.flower.service.SysRoleService;
import com.flower.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yumaoying on 2018/4/15.
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    //角色管理页面
    @RequestMapping
    public String userPage() {
        return "sysRole/sysRoles";
    }

    //角色列表
    @RequestMapping("/roles")
    @ResponseBody
    public Map<String, Object> getAll(SysRole sysRole, String draw,
                                      @RequestParam(required = false, defaultValue = "0") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start / length, length, sort);
        Page page = sysRoleService.findAll(sysRole, pageable);
        map.put("draw", draw);
        map.put("recordsTotal", page.getTotalElements());
        map.put("recordsFiltered", page.getTotalElements());
        map.put("data", page.getContent());
        return map;
    }

    //修改角色信息
    @RequestMapping("/edit")
    @ResponseBody
    public String add(SysRole sysRole) {
        System.out.println("sysRole:" + sysRole);
        try {
            sysRoleService.add(sysRole);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("constraint"))
                return "fail";
            else
                return "error";
        }
    }

    //根据id查找角色信息
    @RequestMapping("/findById")
    @ResponseBody
    public SysRole findById(Integer id) {
        return sysRoleService.findById(id);
    }

    //删除角色
    @RequestMapping("/delete")
    public String deleteRole(Integer id) {
        try {
            sysRoleService.del(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //带选中的角色信息
    @RequestMapping("/rolesWithSelected")
    @ResponseBody
    public List<SysRole> findRoleListByUid(Integer uid) {
        List<SysRole> ur = sysUserService.findRoleListByUid(uid);
        List<SysRole> roleList = sysRoleService.findAll();
        for (int i = 0; i < roleList.size(); i++) {
            for (int j = 0; j < ur.size(); j++) {
                if (roleList.get(i).getId() == ur.get(j).getId()) {
                    roleList.get(i).setSelected(1);
                }
            }
        }
        return roleList;
    }

    //分配权限
    @RequestMapping("/savePermission")
    @ResponseBody
    public String savePermission(Integer rid, @RequestParam("permissionId") String permissionIds) {
        System.out.println("==============rid" + rid + ", permissionIds:" + permissionIds);
        if (StringUtils.isEmpty(rid))
            return "error";
        try {
            sysRoleService.savePermisssion(rid, permissionIds);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
