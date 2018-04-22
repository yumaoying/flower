package com.flower.service;

import com.flower.entity.SysRole;
import com.flower.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 * 用户信息接口
 */
public interface SysUserService {

    //通过username查找用户信息;
    public SysUser findByUsername(String username);

    //按编号查找用户
    public SysUser findByUid(Integer id);

    //保存用户信息
    public void save(SysUser sysUser);

    //根据用户id删除用户信息
    public void del(Integer id);

    //分页查找
    public Page<SysUser> getUserInfoPage(SysUser sysUser, Pageable pageable);

    //更据用户id查找角色
    public List<SysRole> findRoleListByUid(Integer uid);

    //修改用户信息
    public void edit(Integer uid, String username, String name, Integer state);

    //给用户分配角色
    public void addUserRole(Integer uid, Integer[] roleId);

    public int editPassword(Integer uid, String password, String salt);
}
