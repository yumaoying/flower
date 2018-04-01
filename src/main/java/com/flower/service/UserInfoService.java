package com.flower.service;

import com.flower.entity.UserInfo;

import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 * 用户信息接口
 */
public interface UserInfoService {

    //通过username查找用户信息;
    public UserInfo findByUsername(String username);

    //获取所有用户
    public List<UserInfo> userInfoList();

    //保存用户信息
    public void save(UserInfo userInfo);

    //修改用户信息
    public void edit(UserInfo userInfo);

    //根据用户id删除用户信息
    public void del(Integer id);
}
