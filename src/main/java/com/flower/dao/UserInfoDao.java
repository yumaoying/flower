package com.flower.dao;

import com.flower.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yumaoying on 2018/3/24.
 */
public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
    /***
     * 通过用户名查找用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public UserInfo findByUsername(String username);

}
