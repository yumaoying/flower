package com.flower.service;

import com.flower.entity.SysPermission;
import com.flower.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yumaoying on 2018/4/15.
 * 系统角色接口Service
 */
public interface SysRoleService {
    public Page<SysRole> findAll(SysRole sysRole, Pageable pageable);

    public void del(Integer id);

    public void add(SysRole sysRole);

    public void edit(SysRole sysRole);

    public SysRole findByRole(String role);

    public List<SysRole> findAll();

    public SysRole findById(Integer id);

    public void savePermisssion(Integer rid, String permissions);

    public List<SysPermission> findPermissionsById(Integer id);
}
