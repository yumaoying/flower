package com.flower.service.impl;

import com.flower.entity.UserInfo;
import com.flower.dao.UserInfoDao;
import com.flower.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String userName) {
        System.out.println("UserInfoServiceImpl.findByUserName");
        return userInfoDao.findByUsername(userName);
    }

    @Override
    public List<UserInfo> userInfoList() {
        return userInfoDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

    @Override
    public void edit(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

    @Override
    public void del(Integer id) {
        userInfoDao.delete(id);
    }
}
