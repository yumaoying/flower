package com.flower.dao;

import com.flower.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yumaoying on 2018/3/25.
 * UserInfoDao的测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserInfoDaoTest {
    @Resource
    private UserInfoDao userInfoDao;

    @Test
    public void test() {
        List<UserInfo> userInfoList = userInfoDao.findAll();
        for (UserInfo userInfo : userInfoList) {
            System.out.println("=========================================" + userInfo);
        }
    }
}
