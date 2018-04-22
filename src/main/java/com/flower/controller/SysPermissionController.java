package com.flower.controller;

import com.flower.entity.SysPermission;
import com.flower.service.SysPermissionService;
import com.flower.service.SysRoleService;
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
 * Created by yumaoying on 2018/4/22.
 * 权限管理
 */
@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    //权限管理页面
    @RequestMapping
    public String permissionPage() {
        return "sysPermission/sysPermission";
    }

    /***
     * 查询权限信息，若用户已拥有权限，则标记为选中
     * @param id 角色id
     * @return 权限信息
     */
    @RequestMapping("/permissionRole")
    @ResponseBody
    public List<SysPermission> permisssionWithSelected(Integer id) {
        List<SysPermission> slist = sysPermissionService.findByParentIdAfterOrderBySortAsc(0);
        List<SysPermission> rs = sysRoleService.findPermissionsById(id);
        for (int i = 0; i < slist.size(); i++) {
            for (int j = 0; j < rs.size(); j++) {
                if (rs.get(j).getId() == slist.get(i).getId()) {
                    slist.get(i).setChecked("true");
                }
            }
            slist.get(i).setChecked("false");
        }
        System.out.println("id:" + id);
        System.out.println("slist" + slist);
        return slist;
    }

    //权限分页显示
    @RequestMapping("/perms")
    @ResponseBody
    public Map<String, Object> get(SysPermission permission, String draw,
                                   @RequestParam(required = false, defaultValue = "0") int start,
                                   @RequestParam(required = false, defaultValue = "10") int length) {
        Map<String, Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start / length, length, sort);
        Page page = sysPermissionService.getPermissionPages(permission, pageable);
        map.put("draw", draw);
        map.put("recordsTotal", page.getTotalElements());
        map.put("recordsFiltered", page.getTotalElements());
        map.put("data", page.getContent());
        System.out.println("=================" + page.getContent());
        return map;
    }

    //添加权限
    @RequestMapping("/add")
    @ResponseBody
    public String add(SysPermission permission) {
        try {
            sysPermissionService.add(permission);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/findById")
    @ResponseBody
    public SysPermission findByid(Integer id) {
        return sysPermissionService.findById(id);
    }

    //修改权限
    @RequestMapping("/edit")
    @ResponseBody
    public String edit(SysPermission permission) {
        try {
            sysPermissionService.edit(permission);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //删除权限
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Integer id) {
        try {
            sysPermissionService.delete(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
